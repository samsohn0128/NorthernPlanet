package com.pyhu.northernplanet.api.controller;

import com.pyhu.northernplanet.api.request.ScriptReq;
import com.pyhu.northernplanet.api.response.ScriptRes;
import com.pyhu.northernplanet.api.service.SlideService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(value = "슬라이드 관련 API", tags = {"Slide"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/slide")
public class SlideController {

  private final SlideService scriptService;

  @GetMapping("/script")
  @ApiOperation(value = "대본 조회")
  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"),
      @ApiResponse(code = 500, message = "서버 오류")})
  public ResponseEntity getScript(@RequestParam(required = true) Long slideId) {
    log.info("[getScript - controller]");
    try {
      return new ResponseEntity<ScriptRes>(scriptService.getScript(slideId), HttpStatus.OK);
    } catch (Exception e) {
      log.error("[getScript - controller] Failed to get script");
      e.printStackTrace();
      return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/script")
  @ApiOperation(value = "대본 저장 및 수정")
  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"),
      @ApiResponse(code = 500, message = "서버 오류")})
  public ResponseEntity<Integer> updateScript(@RequestBody ScriptReq scriptReq) {
    log.info("[updateScript - controller]");
    try {
      scriptService.updateScript(scriptReq);
      return new ResponseEntity(HttpStatus.OK);
    } catch (Exception e) {
      log.error("[updateScript - controller] Failed to update script");
      e.printStackTrace();
      return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
