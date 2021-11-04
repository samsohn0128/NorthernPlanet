package com.pyhu.northernplanet.api.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SlideUpdateReq {

  private Long slideId;

  private MultipartFile slide;

  private Integer sequence;

  private Integer effect;
}
