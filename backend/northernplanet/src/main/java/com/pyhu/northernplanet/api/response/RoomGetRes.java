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
@ApiModel("RoomGetResponse")
public class RoomGetRes extends RoomDto {

  @ApiModelProperty(example = "방 아이디")
  private Long roomId;
  @ApiModelProperty(example = "방 종료 시간")
  private LocalDateTime endTime;
  @ApiModelProperty(example = "방장 아이디")
  private Long managerId;
  @ApiModelProperty(example = "방장 이름")
  private String managerName;


  @Builder
  public RoomGetRes(String name, String description, LocalDateTime startTime,
      List<ParticipantDto> participants, LocalDateTime endTime, Long managerId,
      String managerName, Long roomId) {
    super(name, description, startTime, participants);
    this.roomId = roomId;
    this.endTime = endTime;
    this.managerId = managerId;
    this.managerName = managerName;
  }
}
