package com.pyhu.northernplanet.api.controller;

import com.pyhu.northernplanet.api.request.PptPdf2PngReq;
import com.pyhu.northernplanet.api.request.PresentationPostReq;
import com.pyhu.northernplanet.api.request.PresentationUpdateReq;
import com.pyhu.northernplanet.api.response.PresentationDetailGetRes;
import com.pyhu.northernplanet.api.response.PresentationListGetRes;
import com.pyhu.northernplanet.api.service.PresentationService;
import com.pyhu.northernplanet.common.dto.PresentationDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(value = "발표자료 관련 API", tags = "Presentation")
@RestController
@CrossOrigin
@RequestMapping("/api/presentation")
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
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/{userId}")
  @ApiOperation(value = "발표자료 리스트 읽기")
  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"),
      @ApiResponse(code = 500, message = "서버 오류")})
  public ResponseEntity<List<PresentationListGetRes>> getPresentationList(
      @PathVariable @ApiParam(value = "사용자 ID", required = true) Long userId) {
    log.info("[getPresentationList - controller] userId : {}", userId);
    List<PresentationListGetRes> presentationListGetResList = new ArrayList<>();
    try {
      List<PresentationDto> presentationDtoList = presentationService.getPresentationList(userId);
      presentationDtoList.forEach(presentationDto -> {
        PresentationListGetRes presentationListGetRes = PresentationListGetRes.builder()
            .presentationId(presentationDto.getPresentationId())
            .presentationName(presentationDto.getPresentationName())
            .size(presentationDto.getSize())
            .uploadTime(presentationDto.getUploadTime())
            .build();
        presentationListGetResList.add(presentationListGetRes);
      });
    } catch (Exception e) {
      log.error("[getPresentationList - controller] Failed to get presentationList");
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(presentationListGetResList, HttpStatus.OK);
  }

  @GetMapping("/{userId}/{presentationId}")
  @ApiOperation(value = "발표자료 상세 보기")
  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"),
      @ApiResponse(code = 500, message = "서버 오류")})
  public ResponseEntity<PresentationDetailGetRes> getPresentationDetail(
      @PathVariable @ApiParam(value = "사용자 ID", required = true) Long userId,
      @PathVariable @ApiParam(value = "발표자료 ID", required = true) Long presentationId) {
    log.info("[getPresentationDetail - controller] userId : {}, presentationId : {}", userId,
        presentationId);
    PresentationDetailGetRes presentationDetailGetRes = null;
    try {
      PresentationDto presentationDto = presentationService.getPresentationDetail(userId,
          presentationId);
      presentationDetailGetRes = PresentationDetailGetRes.builder()
          .presentationId(presentationDto.getPresentationId())
          .presentationName(presentationDto.getPresentationName())
          .size(presentationDto.getSize())
          .uploadTime(presentationDto.getUploadTime())
          .slideList(presentationDto.getSlideList())
          .build();
    } catch (Exception e) {
      log.error("[getPresentationDetail - controller] Failed to get presentationDetail");
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(presentationDetailGetRes, HttpStatus.OK);
  }

  @PutMapping("/{presentationId}")
  @ApiOperation(value = "발표 자료 수정")
  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"),
      @ApiResponse(code = 500, message = "서버 오류")})
  public ResponseEntity<Integer> updatePresentation(
      @ModelAttribute PresentationUpdateReq presentationUpdateReq) {
    log.info("[updatePresentation - controller]");
    try {
      presentationService.updatePresentation(presentationUpdateReq);
    } catch (Exception e) {
      log.error("[updatePresentation - controller] Failed to update presentation");
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/pptpdf")
  @ApiOperation(value = "pptpdf")
  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"),
      @ApiResponse(code = 500, message = "서버 오류")})
  public ResponseEntity<Integer> createPptPdf(@ModelAttribute PptPdf2PngReq pptPdf2PngReq) {
    log.info("[createPpt - controller]");
    String originalFilename = pptPdf2PngReq.getPptPdf().getOriginalFilename();
    String extensionName = originalFilename.substring(originalFilename.lastIndexOf('.'));
    try {
      if (extensionName.equals(".ppt") || extensionName.equals(".pptx")) {
        presentationService.createPpt(pptPdf2PngReq);
      } else if (extensionName.equals(".pdf")) {
        presentationService.createPdf(pptPdf2PngReq);
      }
    } catch (Exception e) {
      log.error("[createPpt - controller] Failed to create presentation");
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
