package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.api.request.ScriptReq;
import com.pyhu.northernplanet.api.response.SlideRes;
import com.pyhu.northernplanet.common.exception.BadRequestException;
import com.pyhu.northernplanet.db.entity.Slide;
import com.pyhu.northernplanet.db.repository.SlideRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("slideService")
@RequiredArgsConstructor
public class SlideServiceImpl implements SlideService {

  private final SlideRepository slideRepository;

  @Override
  public SlideRes getScript(Long slideId) throws IOException {
    Slide slide = slideRepository.findById(slideId).orElse(null);

    if (slide == null) {
      throw new BadRequestException("존재하지 않는 슬라이드 id입니다.");
    }

    SlideRes scriptRes;
    if (slide.getScript() == null) {
      scriptRes = new SlideRes(slide.getSlideId(), slide.getSaveName(),
          slide.getOriginalName(), slide.getDirectory(), "", slide.getSequence());
    } else {
      scriptRes = new SlideRes(slide.getSlideId(), slide.getSaveName(),
          slide.getOriginalName(), slide.getDirectory(), slide.getScript(), slide.getSequence());
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
