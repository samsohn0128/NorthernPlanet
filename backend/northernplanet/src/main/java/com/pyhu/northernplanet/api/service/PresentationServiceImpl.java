package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.api.request.PptToPngReq;
import com.pyhu.northernplanet.api.request.PresentationPostReq;
import com.pyhu.northernplanet.common.dto.PresentationDto;
import com.pyhu.northernplanet.common.dto.SlideDto;
import com.pyhu.northernplanet.db.entity.Presentation;
import com.pyhu.northernplanet.db.entity.Slide;
import com.pyhu.northernplanet.db.entity.User;
import com.pyhu.northernplanet.db.repository.PresentationRepository;
import com.pyhu.northernplanet.db.repository.SlideRepository;
import com.pyhu.northernplanet.db.repository.UserRepository;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("PresentationService")
@RequiredArgsConstructor
@Slf4j
public class PresentationServiceImpl implements PresentationService {

  private final UserRepository userRepository;
  private final PresentationRepository presentationRepository;
  private final SlideRepository slideRepository;

  // ubuntu
//  private final String presentationDirectory = "/home/ubuntu/presentation";
  // dongwoo
  private final String presentationDirectory = "/Users/dongwoosohn/presentation";
  //aeeun
//  private final String presentationDirectory = "/Users/gim-aeeun/file";

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
          .sequence(i)
          .presentation(presentation)
          .build();
      log.info("[createPresentation - service] Slide : {}", slide);
      slides.add(slide);
    }
    slideRepository.saveAll(slides);

    // save presentation files
    String folderDirectory = presentationDirectory + "/" + presentationPostReq.getUserId() + "/"
        + presentation.getPresentationId();
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
    log.info("[getPresentationDetail - service] userId : {}, presentationId : {}", userId,
        presentationId);
    Presentation presentation = presentationRepository.findById(presentationId)
        .orElseThrow(() -> new NullPointerException());
    List<Slide> slideList = slideRepository.findByPresentation_presentationId(presentationId)
        .orElseThrow(() -> new NullPointerException());
    List<SlideDto> slideDtoList = new ArrayList<>();
    slideList.forEach(slide -> {
      byte[] slideByteArray = null;
      try {
        InputStream inputStream = new FileInputStream(slide.getDirectory());
        slideByteArray = IOUtils.toByteArray(inputStream);
        inputStream.close();
      } catch (Exception e) {
        log.error(
            "[getPresentationDetail - service] Failed to get inputStream. slideDirectory : {}",
            slide.getDirectory());
        e.printStackTrace();
      }
      SlideDto slideDto = SlideDto.builder()
          .slideId(slide.getSlideId())
          .sequence(slide.getSequence())
          .script(slide.getScript())
          .slideFile(slideByteArray)
          .build();
      slideDtoList.add(slideDto);
    });

    PresentationDto presentationDto = PresentationDto.builder()
        .presentationId(presentationId)
        .presentationName(presentation.getName())
        .size(presentation.getSize())
        .uploadTime(presentation.getUploadTime())
        .slideList(slideDtoList)
        .build();
    return presentationDto;
  }

  public int createPpt(PptToPngReq pptToPngReq) throws IOException {
    log.info("[createPpt - service]");
    List<Slide> slides = new LinkedList<>();
    MultipartFile convFile = pptToPngReq.getPpt();
    String originalFileName = convFile.getOriginalFilename();
    String pptFolderDirectory = presentationDirectory + "/" + pptToPngReq.getUserId();
    File pptFolder = new File(pptFolderDirectory);
    if (!pptFolder.exists()) {
      pptFolder.mkdirs();
    }

    File pptFile = new File(pptFolderDirectory + "/" + originalFileName);
    convFile.transferTo(pptFile);
    log.info("[createPpt - service] file successfully created");
    XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(pptFile));
    pptFile.delete();

    //getting the dimensions and size of the slide
    Dimension pgSize = ppt.getPageSize();
    List<XSLFSlide> xslfSlideList = ppt.getSlides();

    // save presentation
    User user = userRepository.findById(pptToPngReq.getUserId())
        .orElseThrow(() -> new NullPointerException());
    LocalDateTime now = LocalDateTime.now();
    String presentationName = pptToPngReq.getPpt().getOriginalFilename()
        .substring(0, pptToPngReq.getPpt().getOriginalFilename().lastIndexOf('.'));
    Presentation presentation = Presentation.builder()
        .user(user)
        .name(presentationName)
        .size(xslfSlideList.size())
        .uploadTime(now)
        .build();
    log.info("[createPresentation - service] Presentation : {}", presentation);
    presentation = presentationRepository.saveAndFlush(presentation);

    for (int i = 0; i < xslfSlideList.size(); i++) {
      BufferedImage img = new BufferedImage(pgSize.width, pgSize.height,
          BufferedImage.TYPE_INT_RGB);
      Graphics2D graphics = img.createGraphics();

      //clear the drawing area
      graphics.setPaint(Color.white);
      graphics.fill(new Rectangle2D.Float(0, 0, pgSize.width, pgSize.height));

      //render
      xslfSlideList.get(i).draw(graphics);

      //creating an image file as output
      String folderDirectory = presentationDirectory + "/" + pptToPngReq.getUserId() + "/"
          + presentation.getPresentationId();
      File folder = new File(folderDirectory);
      if (!folder.exists()) {
        folder.mkdirs();
      }
      String saveName = i + ".png";
      // save images
      FileOutputStream out = new FileOutputStream(folderDirectory + "/" + saveName);
      javax.imageio.ImageIO.write(img, "png", out);
      out.close();
      log.info("[createPpt - service] image successfully created");

      // save slides
      Slide slide = Slide.builder()
          .saveName(saveName)
          .originalName(pptFile.getName().substring(0, pptFile.getName().lastIndexOf('.')))
          .directory(folderDirectory + "/" + saveName)
          .sequence(i)
          .presentation(presentation)
          .build();
      slides.add(slide);
      log.info("[createPpt - service] Slide : {}", slide);
      out.close();
    }
    slideRepository.saveAll(slides);

    return 0;
  }
}
