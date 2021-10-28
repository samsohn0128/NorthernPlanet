package com.pyhu.northernplanet.common.dto;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class RoomDto {

  @ApiModelProperty(example = "방 이름")
  String name;
  @ApiModelProperty(example = "방 설명")
  String description;
  @ApiModelProperty(example = "방 시작 시간")
  LocalDateTime startTime;
  @ApiModelProperty(example = "참가자 목록")
  List<ParticipantDto> participants;
}
