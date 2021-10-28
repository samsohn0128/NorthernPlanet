package com.pyhu.northernplanet.common.dto;

import com.pyhu.northernplanet.db.entity.Code;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ParticipantDto {

  @ApiModelProperty(example = "참가자 이름")
  String name;

  @ApiModelProperty(example = "참가자 이메일")
  String email;

  @ApiModelProperty(example = "역할 코드 {CodeId: 역할 아이디, CodeName: 역할 이름}")
  Code code;

}
