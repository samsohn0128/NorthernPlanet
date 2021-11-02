package com.pyhu.northernplanet.api.controller;

import com.pyhu.northernplanet.api.request.PresentationPostReq;
import com.pyhu.northernplanet.api.service.PresentationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(value = "프레젠테이션 관련 API", tags = {"Presentation"})
@RestController
@CrossOrigin
@RequestMapping("/api/presentaion")
@RequiredArgsConstructor
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
      return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Integer>(HttpStatus.OK);
  }
}
