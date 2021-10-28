package com.pyhu.northernplanet.api.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pyhu.northernplanet.api.request.PresentationPostReq;
import com.pyhu.northernplanet.api.service.PresentationService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Api
@RestController
@CrossOrigin
@RequestMapping("/presentaion")
@RequiredArgsConstructor
public class PresentationController {
  private PresentationService presentationService;

  @PostMapping("/")
  @ApiOperation(value = "발표자료 업로드")
  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
          @ApiResponse(code = 401, message = "인증 실패"),
          @ApiResponse(code = 500, message = "서버 오류")})
  public ResponseEntity<Integer> createPresentation(
      @ModelAttribute PresentationPostReq presentationPostReq) {
    int status = presentationService.createPresentation(presentationPostReq);
    switch (status) {
      case 0:
        return new ResponseEntity<Integer>(HttpStatus.OK);
      default:
        return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
