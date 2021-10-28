package com.pyhu.northernplanet.api.request;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class SlideRequest {
  private MultipartFile slide;

  private Integer sequenceNum;
}
