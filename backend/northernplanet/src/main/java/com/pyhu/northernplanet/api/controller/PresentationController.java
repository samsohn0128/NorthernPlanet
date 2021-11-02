package com.pyhu.northernplanet.api.controller;

import com.pyhu.northernplanet.api.request.PPTtoPngReq;
import com.pyhu.northernplanet.api.request.PresentationPostReq;
import com.pyhu.northernplanet.api.service.PresentationService;
import com.pyhu.northernplanet.api.service.Transcoder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@CrossOrigin
@RequestMapping("/api/presentaion")
@RequiredArgsConstructor
@Slf4j
public class PresentationController {

  private PresentationService presentationService;

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

  @PostMapping("/ppt")
  @ApiOperation(value="ppt")
  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
          @ApiResponse(code = 401, message = "인증 실패"),
          @ApiResponse(code = 500, message = "서버 오류")})
  public ResponseEntity<Integer> createPpt(PPTtoPngReq ppTtoPngReq) {
    log.info("[createPpt - controller]");
    try {
      presentationService.createPPt(ppTtoPngReq);
    } catch (Exception e) {
      log.error("[createPresentation - controller] Failed to created presentation");
      e.printStackTrace();
      return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Integer>(HttpStatus.OK);
  }

}
