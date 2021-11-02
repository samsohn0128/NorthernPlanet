package com.pyhu.northernplanet.api.request;

import com.pyhu.northernplanet.common.dto.ParticipantDto;
import com.pyhu.northernplanet.common.dto.RoomDto;
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
public class RoomPutReq extends RoomDto {
  @ApiModelProperty(example="방 아이디")
  private Long roomId;
  @ApiModelProperty(example="끝나는 시간")
  private LocalDateTime endTime;
  @ApiModelProperty(example="방이 활동중인지")
  private Boolean onLive;

  @Builder
  public RoomPutReq(String name, String description, LocalDateTime startTime,
      List<ParticipantDto> participants, LocalDateTime endTime, Boolean onLive, Long roomId) {
    super(name, description, startTime, participants);
    this.roomId=roomId;
    this.endTime=endTime;
    this.onLive=onLive;
  }
}
