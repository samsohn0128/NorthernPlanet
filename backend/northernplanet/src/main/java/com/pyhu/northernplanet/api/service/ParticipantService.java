package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.common.dto.ParticipantDto;
import java.util.List;

public interface ParticipantService {

  /**
   * 방 아이디로 참가자 목록 반환
   *
   * @param roomId
   * @return 참가자 목록 리스트
   */
  List<ParticipantDto> getParticipantByRoomId(Long roomId);

}
