package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.api.request.PPTtoPngReq;
import com.pyhu.northernplanet.api.request.PresentationPostReq;
import java.io.IOException;

public interface PresentationService {

  public int createPresentation(PresentationPostReq presentationPostReq) throws IOException;
  public int createPPt(PPTtoPngReq ppTtoPngReq) throws IOException;

}
