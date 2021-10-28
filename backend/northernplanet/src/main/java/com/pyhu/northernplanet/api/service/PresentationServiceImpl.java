package com.pyhu.northernplanet.api.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.pyhu.northernplanet.api.request.PresentationPostRequest;
import com.pyhu.northernplanet.api.request.SlideRequest;
import com.pyhu.northernplanet.db.entity.Presentation;
import com.pyhu.northernplanet.db.entity.Slide;
import com.pyhu.northernplanet.db.repository.PresentationRepository;
import com.pyhu.northernplanet.db.repository.SlideRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
  public int createPresentation(PresentationPostRequest presentationPostRequest) {
    log.info("[createPresentation - service]");
    // save slides
    List<Slide> slides = new LinkedList<>();
    presentationPostRequest.getSlides().forEach(slideRequest -> {
      String originalFileName = slideRequest.getSlide().getOriginalFilename();
      String extensionName = originalFileName.substring(originalFileName.lastIndexOf('.'));
      Slide slide = Slide.builder().saveName(slideRequest.getSequenceNum() + "")
          .originalName(originalFileName)
          .directory(presentationDirectory + "/" + presentationPostRequest.getUserId() + "/"
              + presentationPostRequest.getPresentationName() + "/" + slideRequest.getSequenceNum()
              + extensionName)
          .build();
      log.info("[createPresentation - service] Slide : {}", slide);
      slides.add(slide);
    });
    try {
      slideRepository.saveAll(slides);
    } catch (Exception e) {
      log.error("[createPresentation - service] Save Slide Error");
      e.printStackTrace();
      return 1;
    }

    // save presentation
    LocalDateTime now = LocalDateTime.now();
    Presentation presentation =
        Presentation.builder().name(presentationPostRequest.getPresentationName())
            .size(presentationPostRequest.getSlides().size()).upload_time(now).build();
    log.info("[createPresentation - service] Presentation : {}", presentation);
    try {
      presentation = presentationRepository.saveAndFlush(presentation);
    } catch (Exception e) {
      log.error("[createPresentation - service] Save Presentation Error");
      e.printStackTrace();
      return 2;
    }
    // save presentation files
    String folderDirectory = presentationDirectory + "/" + presentationPostRequest.getUserId() + "/"
        + presentation.getPresentationId() + "/";
    File folder = new File(folderDirectory);
    if (!folder.exists()) {
      folder.mkdirs();
    }


    for (int i = 0; i < presentationPostRequest.getSlides().size(); i++) {
      SlideRequest slideRequest = presentationPostRequest.getSlides().get(i);
      String originalFileName = slideRequest.getSlide().getOriginalFilename();
      String extensionName = originalFileName.substring(originalFileName.lastIndexOf('.'));
      File slide = new File(folderDirectory + slideRequest.getSequenceNum() + extensionName);
      try {
        slideRequest.getSlide().transferTo(slide);
      } catch (IllegalStateException | IOException e) {
        log.error("[createPresentation - service] Save Slide File Error");
        e.printStackTrace();
        return 3;
      }
    }

    return 0;
  }

}
