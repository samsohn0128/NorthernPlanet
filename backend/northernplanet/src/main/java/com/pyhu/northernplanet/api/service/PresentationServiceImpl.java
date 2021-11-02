package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.api.request.PPTtoPngReq;
import com.pyhu.northernplanet.api.request.PresentationPostReq;
import com.pyhu.northernplanet.api.request.SlideRequest;
import com.pyhu.northernplanet.db.entity.Presentation;
import com.pyhu.northernplanet.db.entity.Slide;
import com.pyhu.northernplanet.db.repository.PresentationRepository;
import com.pyhu.northernplanet.db.repository.SlideRepository;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
  private final String aeeunpath="/Users/gim-aeeun/file";
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

  @Override
  public int createPPt(PPTtoPngReq ppTtoPngReq) throws IOException {
    List<Slide> slides = new LinkedList<>();
    int cnt = 1;
    MultipartFile convfile= ppTtoPngReq.getPpt();
    File file = new File(aeeunpath+"/"+convfile.getOriginalFilename());
    file.createNewFile();
    XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(file));
    System.out.println("file successfully created");

    //getting the dimensions and size of the slide
    Dimension pgsize = ppt.getPageSize();
    java.util.List<XSLFSlide> slide = ppt.getSlides();

    for (int i = 0; i < slide.size(); i++) {
      BufferedImage img = new BufferedImage(pgsize.width, pgsize.height,BufferedImage.TYPE_INT_RGB);
      Graphics2D graphics = img.createGraphics();

      //clear the drawing area
      graphics.setPaint(Color.white);
      graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

      //render
      slide.get(i).draw(graphics);

      //creating an image file as output
      FileOutputStream out = new FileOutputStream(aeeunpath+"/ppt_image_" + i + ".png");
      javax.imageio.ImageIO.write(img, "png", out);
      Slide slid = Slide.builder().saveName(i + ".png")
                      .originalName(file.getName())
                              .directory(aeeunpath+"/ppt_image_" + i + ".png").build();
      slides.add(slid);
      System.out.println("Image successfully created");
      out.close();

  }


    slideRepository.saveAll(slides);

    // save presentation
    LocalDateTime now = LocalDateTime.now();
    Presentation presentation =
            Presentation.builder().name(file.getName())
                    .size(slide.size()).upload_time(now).build();
    presentation = presentationRepository.saveAndFlush(presentation);
    // save presentation files
    String folderDirectory = presentationDirectory + "/" + ppTtoPngReq.getUserId() + "/"
            + presentation.getPresentationId() + "/";
    File folder = new File(folderDirectory);
    if (!folder.exists()) {
      folder.mkdirs();
    }

    for (int i = 0; i < slide.size(); i++) {
      Slide slide1 = slides.get(i);
      String originalFileName = slide1.getOriginalName();
      String extensionName = "png";
      File slide2 = new File(folderDirectory + slide1.getSequence() + extensionName);
      slide2.setWritable(true);
      slide2.setReadable(true);
    }

    return 0;
  }

}
