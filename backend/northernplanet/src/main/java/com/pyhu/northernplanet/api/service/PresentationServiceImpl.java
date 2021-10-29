package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.api.request.PresentationPostReq;
import com.pyhu.northernplanet.api.request.SlideRequest;
import com.pyhu.northernplanet.db.entity.Presentation;
import com.pyhu.northernplanet.db.entity.Slide;
import com.pyhu.northernplanet.db.repository.PresentationRepository;
import com.pyhu.northernplanet.db.repository.SlideRepository;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PresentationServiceImpl implements PresentationService {

  private final PresentationRepository presentationRepository;
  private final SlideRepository slideRepository;
  // ubuntu
  // private final String presentationDirectory = "/home/ubuntu/presentation";
  // mac
  private final String presentationDirectory = "/Users/dongwoosohn/presentation";

  @Override
  public int createPresentation(PresentationPostReq presentationPostReq) throws IOException {
    log.info("[createPresentation - service]");
    // save slides
    List<Slide> slides = new LinkedList<>();
    presentationPostReq.getSlides().forEach(slideRequest -> {
      String originalFileName = slideRequest.getSlide().getOriginalFilename();
      String extensionName = originalFileName.substring(originalFileName.lastIndexOf('.'));
      Slide slide = Slide.builder().saveName(slideRequest.getSequenceNum() + "")
          .originalName(originalFileName)
          .directory(presentationDirectory + "/" + presentationPostReq.getUserId() + "/"
              + presentationPostReq.getPresentationName() + "/" + slideRequest.getSequenceNum()
              + extensionName)
          .build();
      log.info("[createPresentation - service] Slide : {}", slide);
      slides.add(slide);
    });
    slideRepository.saveAll(slides);

    // save presentation
    LocalDateTime now = LocalDateTime.now();
    Presentation presentation =
        Presentation.builder().name(presentationPostReq.getPresentationName())
            .size(presentationPostReq.getSlides().size()).upload_time(now).build();
    log.info("[createPresentation - service] Presentation : {}", presentation);
    presentation = presentationRepository.saveAndFlush(presentation);
    // save presentation files
    String folderDirectory = presentationDirectory + "/" + presentationPostReq.getUserId() + "/"
        + presentation.getPresentationId() + "/";
    File folder = new File(folderDirectory);
    if (!folder.exists()) {
      folder.mkdirs();
    }

    for (int i = 0; i < presentationPostReq.getSlides().size(); i++) {
      SlideRequest slideRequest = presentationPostReq.getSlides().get(i);
      String originalFileName = slideRequest.getSlide().getOriginalFilename();
      String extensionName = originalFileName.substring(originalFileName.lastIndexOf('.'));
      File slide = new File(folderDirectory + slideRequest.getSequenceNum() + extensionName);
      slideRequest.getSlide().transferTo(slide);
    }

    return 0;
  }

}
