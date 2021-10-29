package com.pyhu.northernplanet.api.request;

import java.util.List;
import lombok.Data;

@Data
public class PresentationPostReq {
  private Long userId;

  private String presentationName;

  private List<SlideRequest> slides;
}
