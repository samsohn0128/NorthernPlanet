package com.pyhu.northernplanet.api.controller;

import com.pyhu.northernplanet.api.request.UserPutReq;
import com.pyhu.northernplanet.api.service.UserService;
import com.pyhu.northernplanet.common.dto.UserOauthDto;
import com.pyhu.northernplanet.db.entity.User;
import com.pyhu.northernplanet.security.CurrentUser;
import com.pyhu.northernplanet.security.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;


  @ApiOperation(value = "사용자 정보", notes = "인증된 사용자의 정보를 반환합니다.")
  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"), @ApiResponse(code = 404, message = "페이지 없음"),
      @ApiResponse(code = 500, message = "서버 오류")})
  @PreAuthorize("hasRole('USER')")
  @GetMapping("/oauth2/login")
  public ResponseEntity<UserOauthDto> getCurrentUser(
      @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
    log.info("getCurrentUser: userPrincipal - {}", userPrincipal);
    UserOauthDto user = null;
    try {
      user = userService.getOauthUserByOauthId(userPrincipal.getPassword());
      return new ResponseEntity<>(user, HttpStatus.OK);
    } catch (Exception e) {
      log.error("[getCurrentUser] ", e);
    }
    return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
  }


  @GetMapping("/search/{email}")
  @ApiOperation(value = "이메일로 사용자 검색")
  @ApiResponses({
      @ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"),
      @ApiResponse(code = 404, message = "사용자 없음"),
      @ApiResponse(code = 500, message = "서버 오류")
  })
  public ResponseEntity<UserOauthDto> getUserByEmail(@PathVariable("email") String email) {
    UserOauthDto userOauthDto = null;
    try {
      userOauthDto = userService.getUserByEmail(email);
      return new ResponseEntity<>(userOauthDto, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseEntity<>(userOauthDto, HttpStatus.NOT_FOUND);

  }


  @PutMapping("/{userId}")
  @ApiOperation(value = "사용자 아이디로 이름 변경")
  @ApiResponses({
      @ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"),
      @ApiResponse(code = 404, message = "사용자 없음"),
      @ApiResponse(code = 500, message = "서버 오류")
  })
  public ResponseEntity<?> updateName(@PathVariable("userId") Long userId,
      @RequestBody @ApiParam(value = "수정", required = true) UserPutReq updateInfo) {
    try{
      userService.updateUserName(userId, updateInfo.getName());
      return new ResponseEntity<>(HttpStatus.OK);
    }catch (Exception e){
      e.printStackTrace();
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }


  @DeleteMapping("/{userId}")
  @ApiOperation(value = "사용자 아이디로 회원탈퇴")
  @ApiResponses({
      @ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"),
      @ApiResponse(code = 404, message = "사용자 없음"),
      @ApiResponse(code = 500, message = "서버 오류")
  })
  public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {

    try{
      userService.deleteUser(userId);
      return new ResponseEntity<>(HttpStatus.OK);
    }catch (Exception e){
      e.printStackTrace();
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }
}
