package com.pyhu.northernplanet.api.request;

import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.Data;

@Data
public class PresentationUpdateReq {

  @ApiParam(value = "사용자 ID", required = true)
  private Long userId;

  @ApiParam(value = "발표 자료 ID", required = true)
  private Long presentationId;

  @ApiParam(value = "발표 자료", required = true)
  private List<SlideUpdateReq> slides;
}
