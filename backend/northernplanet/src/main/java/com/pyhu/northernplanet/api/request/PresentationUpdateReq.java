package com.pyhu.northernplanet.api.request;

import java.util.List;
import lombok.Data;

@Data
public class PresentationUpdateReq {

  private Long userId;

  private List<SlideUpdateReq> slides;
}
