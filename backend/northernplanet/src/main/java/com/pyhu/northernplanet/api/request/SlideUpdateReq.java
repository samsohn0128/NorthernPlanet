package com.pyhu.northernplanet.api.request;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class SlideUpdateReq {

  @ApiParam(value = "슬라이드 ID", required = true)
  private Long slideId;

  @ApiParam(value = "슬라이드 번호", required = true)
  private Integer sequence;

  @ApiParam(value = "발표 자료 전환 효과", required = true)
  private Integer effect;
}
