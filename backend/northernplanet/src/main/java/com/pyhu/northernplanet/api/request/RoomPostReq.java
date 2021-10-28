package com.pyhu.northernplanet.api.request;

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
@ApiModel("RoomRegisterPostRequest")
public class RoomPostReq extends RoomDto {
	@ApiModelProperty(name="방장 이메일", example = "방장 이메일")
	String email;

	@Builder
	public RoomPostReq(String name, String description, LocalDateTime startTime,
			List<ParticipantDto> participants, String email) {
		super(name, description, startTime, participants);
		this.email=email;
	}
}
