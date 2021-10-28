package com.pyhu.northernplanet.api.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.pyhu.northernplanet.common.dto.ParticipantDto;
import com.pyhu.northernplanet.db.entity.Participant;
import com.pyhu.northernplanet.db.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service("participantService")
public class ParticipantServiceImpl implements ParticipantService {

  private final ParticipantRepository participantRepository;

  @Override
  public List<ParticipantDto> getParticipantByRoomId(Long roomId) {
    List<Participant> participants = participantRepository.findByrooms_roomId(roomId);
    List<ParticipantDto> participantGetReqList = new ArrayList<>();

    for (Participant item : participants) {
      participantGetReqList.add(ParticipantDto.builder().name(item.getUser().getName())
          .email(item.getUser().getEmail()).code(item.getCode()).build());
    }
    return participantGetReqList;
  }

}
