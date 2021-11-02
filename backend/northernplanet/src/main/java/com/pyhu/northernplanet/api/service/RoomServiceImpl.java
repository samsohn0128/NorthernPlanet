package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.api.request.RoomPutReq;
import com.pyhu.northernplanet.api.response.RoomPutRes;
import java.sql.Timestamp;
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
@Service("RoomService")
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

  private final RoomRepository roomRepository;

  private final UserRepository userRepository;

  private final ParticipantRepository participantRepository;


  @Override
  public RoomGetRes getRoom(Long roomId, List<ParticipantDto> participants) {
    Room room = roomRepository.getById(roomId);

    RoomGetRes roomget = RoomGetRes.builder()
        .roomId(room.getRoomId())
        .name(room.getName())
        .description(room.getDescription())
        .startTime(room.getStartTime())
        .managerId(room.getUser().getUserId())
        .managerName(room.getUser().getName())
        .participants(participants)
        .build();
    if (room.getEndTime() == null) {
      roomget.setEndTime(null);
    } else {
      roomget.setEndTime(room.getEndTime());
    }
    return roomget;
  }

  @Override
  public Room getRoom(Long roomId) {
    Room room = roomRepository.getById(roomId);
    return room;
  }

  @Override
  public List<RoomGetRes> getRoomListByUserId(Long userId) {
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

  @Override
  public void deleteRoom(Long roomId) {
    int ret = participantRepository.deleteAllByRoom_RoomId(roomId);
    log.info("[DeleteRoom] All participants in room {} are deleted - service - {}", roomId, ret);
    int ret2 = roomRepository.deleteAllByRoomId(roomId);
    log.info("[DeleteRoom] Room {} is deleted - service - {}", roomId, ret2);
  }

  @Override
  public RoomPutRes updateRoom(RoomPutReq roomInfo) {
    Room room = getRoom(roomInfo.getRoomId());
    room.setStartTime(roomInfo.getStartTime());
    room.setName(roomInfo.getName());
    room.setDescription(roomInfo.getDescription());
    participantRepository.deleteAllByRoom_RoomId(roomInfo.getRoomId());
    saveParticipants(roomInfo.getParticipants(), room);
    roomRepository.save(room);
    RoomPutRes roomPutRes=RoomPutRes.builder()
        .roomId(room.getRoomId())
        .name(room.getName())
        .description(room.getDescription())
        .participants(roomInfo.getParticipants())
        .startTime(room.getStartTime())
        .endTime(room.getEndTime())
        .managerId(room.getUser().getUserId())
        .managerName(room.getUser().getName())
        .build();
    return roomPutRes;
  }

  private void saveParticipants(List<ParticipantDto> person, Room room) {
    Participant participant;
    Code code;

    int size = person.size();
    for (int i = 0; i < size; i++) {
      log.info("[saveParticipants] i:{},  person: {}", i, person.get(i));

      code = Code.builder().codeId(person.get(i).getCode().getCodeId())
          .codeName(person.get(i).getCode().getCodeName()).build();

      participant = Participant.builder()
          .code(code)
          .user(userRepository.findByEmail(person.get(i).getEmail()))
          .room(room).build();
      log.info("[saveParticipants] participant: {}", participant);
      participantRepository.save(participant);
    }

  }
}
