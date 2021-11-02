package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.api.request.ScriptReq;
import com.pyhu.northernplanet.api.response.SlideRes;
import java.io.IOException;

public interface SlideService {

  SlideRes getScript(Long slideId) throws IOException;

  void updateScript(ScriptReq scriptReq) throws IOException;

}
