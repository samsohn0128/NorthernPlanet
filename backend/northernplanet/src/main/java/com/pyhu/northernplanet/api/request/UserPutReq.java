package com.pyhu.northernplanet.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserPutReq {

  @ApiModelProperty(example = "사용자 이름")
  private String name;
}
