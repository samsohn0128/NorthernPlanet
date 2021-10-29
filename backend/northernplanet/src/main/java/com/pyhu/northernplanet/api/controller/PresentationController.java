package com.pyhu.northernplanet.api.controller;

import com.pyhu.northernplanet.api.request.PresentationPostReq;
import com.pyhu.northernplanet.api.service.PresentationService;
import com.pyhu.northernplanet.common.dto.PresentationDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@CrossOrigin
@RequestMapping("/api/presentation")
@RequiredArgsConstructor
@Slf4j
public class PresentationController {

  private final PresentationService presentationService;

  @PostMapping("/")
  @ApiOperation(value = "발표자료 업로드")
  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"),
      @ApiResponse(code = 500, message = "서버 오류")})
  public ResponseEntity<Integer> createPresentation(
      @ModelAttribute PresentationPostReq presentationPostReq) {
    log.info("[createPresentation - controller]");
    try {
      presentationService.createPresentation(presentationPostReq);
    } catch (Exception e) {
      log.error("[createPresentation - controller] Failed to created presentation");
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/{userId}")
  @ApiOperation(value = "발표자료 리스트 읽기")
  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"),
      @ApiResponse(code = 500, message = "서버 오류")})
  public ResponseEntity<List<PresentationDto>> getPresentationList(@PathVariable Long userId) {
    log.info("[getPresentationList - controller] userId : {}", userId);
    List<PresentationDto> presentationDtoList;
    try {
      presentationDtoList = presentationService.getPresentationList(userId);
    } catch (Exception e) {
      log.error("[getPresentationList - controller] Failed to get presentationList");
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(presentationDtoList, HttpStatus.OK);
  }
}
