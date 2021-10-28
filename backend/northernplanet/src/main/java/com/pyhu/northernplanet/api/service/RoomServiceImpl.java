package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.api.request.RoomPostReq;
import com.pyhu.northernplanet.api.response.RoomGetRes;
import com.pyhu.northernplanet.common.dto.ParticipantDto;
import com.pyhu.northernplanet.common.dto.RoomDto;
import com.pyhu.northernplanet.db.entity.Code;
import com.pyhu.northernplanet.db.entity.Participants;
import com.pyhu.northernplanet.db.entity.Rooms;
import com.pyhu.northernplanet.db.entity.Users;
import com.pyhu.northernplanet.db.repository.ParticipantRepository;
import com.pyhu.northernplanet.db.repository.RoomRepository;
import com.pyhu.northernplanet.db.repository.UserRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("roomService")
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

  private final RoomRepository roomRepository;

  private final UserRepository userRepository;

  private final ParticipantRepository participantRepository;


  @Override
  public Rooms getRoom(int roomId) {
    Rooms room = roomRepository.getById(roomId);
    return room;
  }

  @Override
  public List<RoomGetRes> findbyuser(Long userId) {
    List<RoomGetRes> roomres = new ArrayList<>();
    List<Participants> participants = participantRepository.findByusers_userId(userId);
    log.debug("[findbyuser] userId: {}, pa:{}", userId, participants);
    RoomGetRes roomGetRes;
    Rooms room;
    for (Participants item : participants) {
      room = item.getRooms();
      roomGetRes = RoomGetRes.builder()
          .name(room.getName())
          .description(room.getDescription())
          .startTime(room.getStartTime().toLocalDateTime())
          .managerId(room.getUsers().getUserId())
          .managerName(room.getUsers().getName())
          .roomId(room.getRoomId())
          .build();
      if (room.getEndTime() == null) {
        roomGetRes.setEndTime(null);
      } else {
        roomGetRes.setEndTime(room.getEndTime().toLocalDateTime());
      }
      roomres.add(roomGetRes);
    }
    return roomres;
  }

  @Override
  public void createRoom(RoomPostReq roomInfo) {
    log.info("[createRoom] room post req: {}", roomInfo);
    Rooms room = Rooms.builder()
        .name(roomInfo.getName())
        .onLive(false)
        .description(roomInfo.getDescription())
        .users(userRepository.findByEmail(roomInfo.getEmail()))
        .build();

    if (roomInfo.getStartTime() == null) {
      LocalDateTime datetime = LocalDateTime.now();
      room.setStartTime(Timestamp.valueOf(datetime));
    } else {
      room.setStartTime(Timestamp.valueOf(roomInfo.getStartTime()));
    }

    log.info("[createRoom] set users : {}", room);

    roomRepository.save(room);
    log.info("[createRoom] save room complete");

    saveParticipants(roomInfo.getParticipants(), room);
    log.info("[createRoom] save participants complete");
  }

  private void saveParticipants(List<ParticipantDto> person, Rooms room) {
    Participants participant;
    Code code;

    int size = person.size();
    for (int i = 0; i < size; i++) {
      log.info("[saveParticipants] i:{},  person: {}", i, person.get(i));

      code = Code.builder()
          .codeId(person.get(i).getCode().getCodeId())
          .codeName(person.get(i).getCode().getCodeName())
          .build();

      participant = Participants.builder()
          .code(code)
          .users(userRepository.findByEmail(person.get(i).getEmail()))
          .rooms(room)
          .build();
      log.info("[saveParticipants] participant: {}", participant);
      participantRepository.save(participant);
    }

  }
}
