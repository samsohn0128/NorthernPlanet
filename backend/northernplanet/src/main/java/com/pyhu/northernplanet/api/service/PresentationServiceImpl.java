package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.api.request.PresentationPostReq;
import com.pyhu.northernplanet.common.dto.PresentationDto;
import com.pyhu.northernplanet.common.dto.SlideDto;
import com.pyhu.northernplanet.db.entity.Presentation;
import com.pyhu.northernplanet.db.entity.Slide;
import com.pyhu.northernplanet.db.entity.User;
import com.pyhu.northernplanet.db.repository.PresentationRepository;
import com.pyhu.northernplanet.db.repository.SlideRepository;
import com.pyhu.northernplanet.db.repository.UserRepository;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class PresentationServiceImpl implements PresentationService {

  private final UserRepository userRepository;
  private final PresentationRepository presentationRepository;
  private final SlideRepository slideRepository;
  // ubuntu
  // private final String presentationDirectory = "/home/ubuntu/presentation";
  // mac
  private final String presentationDirectory = "/Users/dongwoosohn/presentation";

  @Override
  public int createPresentation(PresentationPostReq presentationPostReq) throws IOException {
    log.info("[createPresentation - service]");

    // save presentation
    User user = userRepository.findById(presentationPostReq.getUserId())
        .orElseThrow(() -> new NullPointerException());
    LocalDateTime now = LocalDateTime.now();
    String presentationName = presentationPostReq.getSlides().get(0).getOriginalFilename()
        .substring(0,
            presentationPostReq.getSlides().get(0).getOriginalFilename().lastIndexOf('.'));
    Presentation presentation = Presentation.builder()
        .user(user)
        .name(presentationName)
        .size(presentationPostReq.getSlides().size())
        .uploadTime(now)
        .build();
    log.info("[createPresentation - service] Presentation : {}", presentation);
    presentation = presentationRepository.saveAndFlush(presentation);

    // save slides
    List<Slide> slides = new LinkedList<>();
    for (int i = 0; i < presentationPostReq.getSlides().size(); i++) {
      MultipartFile slideFile = presentationPostReq.getSlides().get(i);
      String originalFileName = slideFile.getOriginalFilename();
      String extensionName = originalFileName.substring(originalFileName.lastIndexOf('.'));
      String saveName = i + extensionName;
      Slide slide = Slide.builder()
          .saveName(saveName)
          .originalName(originalFileName)
          .directory(presentationDirectory + "/" + presentationPostReq.getUserId() + "/"
              + presentation.getPresentationId() + "/" + saveName)
          .build();
      log.info("[createPresentation - service] Slide : {}", slide);
      slides.add(slide);
    }
    slideRepository.saveAll(slides);

    // save presentation files
    String folderDirectory = presentationDirectory + "/" + presentationPostReq.getUserId() + "/"
        + presentation.getPresentationId() + "/";
    File folder = new File(folderDirectory);
    if (!folder.exists()) {
      folder.mkdirs();
    }

    for (int i = 0; i < presentationPostReq.getSlides().size(); i++) {
      MultipartFile slideFile = presentationPostReq.getSlides().get(i);
      String originalFileName = slideFile.getOriginalFilename();
      String extensionName = originalFileName.substring(originalFileName.lastIndexOf('.'));
      File slide = new File(folderDirectory + "/" + i + extensionName);
      slideFile.transferTo(slide);
    }
    return 0;
  }

  @Override
  public List<PresentationDto> getPresentationList(Long userId) {
    log.info("[getPresentationList - service] userId : {}", userId);
    List<Presentation> presentationList = presentationRepository.findByUser_userId(userId)
        .orElseThrow(() -> new RuntimeException());
    System.out.println(presentationList.size());
    List<PresentationDto> presentationDtoList = new ArrayList<>();
    presentationList.forEach(presentation -> {
      PresentationDto presentationDto = PresentationDto.builder()
          .presentationId(presentation.getPresentationId())
          .presentationName(presentation.getName())
          .size(presentation.getSize())
          .uploadTime(presentation.getUploadTime())
          .build();
      presentationDtoList.add(presentationDto);
      log.info("[getPresentationList - service] presentationId : {}",
          presentation.getPresentationId());
    });
    return presentationDtoList;
  }

  @Override
  public PresentationDto getPresentationDetail(Long userId, Long presentationId) {
    log.info("[getPresentation - service] userId : {}, presentationId : {}", userId,
        presentationId);
    Presentation presentation = presentationRepository.findById(presentationId)
        .orElseThrow(() -> new NullPointerException());
    List<Slide> slideList = slideRepository.findByPresentation_presentationId(presentationId)
        .orElseThrow(() -> new NullPointerException());
    List<SlideDto> slideDtoList = new ArrayList<>();
    slideList.forEach(slide -> {
      File slideFile = new File(slide.getDirectory());
      if (slideFile.exists()) {
        try {
        } catch (Exception e) {
          log.error("getPresentation - service] Failed slideResource");
          e.printStackTrace();
        }
      } else {
        log.error("[getPresentation - service] no such file \"{}\"", slide.getDirectory());
      }
      SlideDto slideDto = SlideDto.builder()
          .sequence(slide.getSequence())
          .script(slide.getScript())
          .build();
      slideDtoList.add(slideDto);
    });

    PresentationDto presentationDto = PresentationDto.builder()
        .presentationId(presentationId)
        .presentationName(presentation.getName())
        .uploadTime(presentation.getUploadTime())
        .size(presentation.getSize())
        .build();
    return presentationDto;
  }
}
