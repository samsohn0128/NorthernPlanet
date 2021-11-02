package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.api.request.PresentationPostReq;
import java.io.IOException;

public interface PresentationService {

  int createPresentation(PresentationPostReq presentationPostReq) throws IOException;
}
