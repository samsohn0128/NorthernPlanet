package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.api.request.ScriptReq;
import com.pyhu.northernplanet.api.response.ScriptRes;
import java.io.IOException;

public interface SlideService {

  ScriptRes getScript(Long slideId) throws IOException;

  void updateScript(ScriptReq scriptReq) throws IOException;

}
