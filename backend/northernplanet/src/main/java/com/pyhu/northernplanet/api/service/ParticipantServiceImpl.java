package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.common.dto.ParticipantDto;
import com.pyhu.northernplanet.db.entity.Participants;
import com.pyhu.northernplanet.db.repository.ParticipantRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service("participantService")
public class ParticipantServiceImpl implements ParticipantService {

  private final ParticipantRepository participantRepository;

  @Override
  public List<ParticipantDto> getParticipantByRoomId(Long roomId) {
    List<Participants> participants = participantRepository.findByrooms_roomId(roomId);
    List<ParticipantDto> participantGetReqList = new ArrayList<>();

    for (Participants item : participants) {
      participantGetReqList.add(ParticipantDto.builder()
          .name(item.getUsers().getName())
          .email(item.getUsers().getEmail())
          .code(item.getCode()).build());
    }
    return participantGetReqList;
  }

}
