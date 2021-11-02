package com.pyhu.northernplanet.api.response;

import com.pyhu.northernplanet.common.dto.ParticipantDto;
import com.pyhu.northernplanet.common.dto.RoomDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel("RoomPutResponse")
public class RoomPutRes extends RoomDto {

  @ApiModelProperty(example = "방 아이디")
  Long roomId;
  @ApiModelProperty(example = "방 종료 시간")
  LocalDateTime endTime;
  @ApiModelProperty(example = "방장 아이디")
  Long managerId;
  @ApiModelProperty(example = "방장 이름")
  String managerName;

  @Builder
  public RoomPutRes(String name, String description, LocalDateTime startTime,
      List<ParticipantDto> participants, Long roomId, LocalDateTime endTime, Long managerId, String managerName) {
    super(name, description, startTime, participants);
    this.roomId=roomId;
    this.endTime=endTime;
    this.managerId=managerId;
    this.managerName=managerName;
  }
}
