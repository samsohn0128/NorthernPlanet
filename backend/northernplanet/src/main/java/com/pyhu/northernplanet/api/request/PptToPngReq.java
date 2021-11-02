package com.pyhu.northernplanet.api.request;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data

public class PptToPngReq {

  @ApiParam(value = "사용자 ID", required = true)
  private Long userId;

  @ApiParam(value = "발표 자료", required = true)
  private MultipartFile ppt;

}
