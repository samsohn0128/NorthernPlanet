package com.pyhu.northernplanet.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.pyhu.northernplanet.api.request.RoomPostReq;
import com.pyhu.northernplanet.api.response.RoomGetRes;
import com.pyhu.northernplanet.common.dto.ParticipantDto;
import com.pyhu.northernplanet.db.entity.Code;
import com.pyhu.northernplanet.db.entity.Participant;
import com.pyhu.northernplanet.db.entity.Room;
import com.pyhu.northernplanet.db.repository.ParticipantRepository;
import com.pyhu.northernplanet.db.repository.RoomRepository;
import com.pyhu.northernplanet.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("roomService")
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

  private final RoomRepository roomRepository;

  private final UserRepository userRepository;

  private final ParticipantRepository participantRepository;


  @Override
  public Room getRoom(Long roomId) {
    Room room = roomRepository.getById(roomId);
    return room;
  }

  @Override
  public List<RoomGetRes> findbyuser(Long userId) {
    List<RoomGetRes> roomres = new ArrayList<>();
    List<Participant> participants = participantRepository.findByUser_userId(userId);
    log.debug("[findbyuser] userId: {}, pa:{}", userId, participants);
    RoomGetRes roomGetRes;
    Room room;
    for (Participant item : participants) {
      room = item.getRoom();
      roomGetRes = RoomGetRes.builder().name(room.getName()).description(room.getDescription())
          .startTime(room.getStartTime()).managerId(room.getUser().getUserId())
          .managerName(room.getUser().getName()).roomId(room.getRoomId()).build();
      if (room.getEndTime() == null) {
        roomGetRes.setEndTime(null);
      } else {
        roomGetRes.setEndTime(room.getEndTime());
      }
      roomres.add(roomGetRes);
    }
    return roomres;
  }

  @Override
  public void createRoom(RoomPostReq roomInfo) {
    log.info("[createRoom] room post req: {}", roomInfo);
    Room room =
        Room.builder().name(roomInfo.getName()).onLive(false).description(roomInfo.getDescription())
            .user(userRepository.findByEmail(roomInfo.getEmail())).build();

    if (roomInfo.getStartTime() == null) {
      LocalDateTime datetime = LocalDateTime.now();
      room.setStartTime(datetime);
    } else {
      room.setStartTime(roomInfo.getStartTime());
    }

    log.info("[createRoom] set users : {}", room);

    roomRepository.save(room);
    log.info("[createRoom] save room complete");

    saveParticipants(roomInfo.getParticipants(), room);
    log.info("[createRoom] save participants complete");
  }

  private void saveParticipants(List<ParticipantDto> person, Room room) {
    Participant participant;
    Code code;

    int size = person.size();
    for (int i = 0; i < size; i++) {
      log.info("[saveParticipants] i:{},  person: {}", i, person.get(i));

      code = Code.builder().codeId(person.get(i).getCode().getCodeId())
          .codeName(person.get(i).getCode().getCodeName()).build();

      participant = Participant.builder().code(code)
          .user(userRepository.findByEmail(person.get(i).getEmail())).room(room).build();
      log.info("[saveParticipants] participant: {}", participant);
      participantRepository.save(participant);
    }

  }
}
