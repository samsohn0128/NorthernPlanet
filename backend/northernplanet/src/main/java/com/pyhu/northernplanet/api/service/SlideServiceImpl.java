package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.api.request.ScriptReq;
import com.pyhu.northernplanet.api.response.ScriptRes;
import com.pyhu.northernplanet.common.exception.BadRequestException;
import com.pyhu.northernplanet.db.entity.Slide;
import com.pyhu.northernplanet.db.repository.SlideRepository;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("slideService")
@RequiredArgsConstructor
public class SlideServiceImpl implements SlideService {

  private final SlideRepository slideRepository;

  @Override
  public ScriptRes getScript(Long slideId) throws IOException {
    Optional<Slide> currentSlide = slideRepository.findById(slideId);

    if (currentSlide.get() == null) {
      throw new BadRequestException("존재하지 않는 슬라이드 id입니다.");
    }

    ScriptRes scriptRes;
    if (currentSlide.get().getScript() == null) {
      scriptRes = new ScriptRes(slideId, "");
    } else {
      scriptRes = new ScriptRes(slideId, currentSlide.get().getScript());
    }

    return scriptRes;
  }

  @Override
  public void updateScript(ScriptReq scriptReq) throws IOException {

    Slide updateSlide = slideRepository.findById(scriptReq.getSlideId()).orElse(null);

    if (updateSlide == null) {
      throw new BadRequestException("존재하지 않는 슬라이드 id입니다.");
    }

    updateSlide.setScript(scriptReq.getScript());

    slideRepository.save(updateSlide);

  }
}
