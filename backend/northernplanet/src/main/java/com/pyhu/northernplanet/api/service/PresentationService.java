package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.api.request.PptPdf2PngReq;
import com.pyhu.northernplanet.api.request.PresentationPostReq;
import com.pyhu.northernplanet.common.dto.PresentationDto;
import java.io.IOException;
import java.util.List;

public interface PresentationService {

  int createPresentation(PresentationPostReq presentationPostReq) throws IOException;

  List<PresentationDto> getPresentationList(Long userId);

  PresentationDto getPresentationDetail(Long userId, Long presentationId);

  int createPpt(PptPdf2PngReq pptPdf2PngReq) throws IOException;

  int createPdf(PptPdf2PngReq pptPdf2PngReq) throws IOException;

}
