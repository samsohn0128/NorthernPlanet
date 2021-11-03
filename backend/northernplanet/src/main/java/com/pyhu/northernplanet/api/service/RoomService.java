package com.pyhu.northernplanet.api.service;


import com.pyhu.northernplanet.api.request.RoomPutReq;
import com.pyhu.northernplanet.api.response.RoomPutRes;
import com.pyhu.northernplanet.common.dto.ParticipantDto;
import com.pyhu.northernplanet.db.entity.Room;
import java.util.List;
import com.pyhu.northernplanet.api.request.RoomPostReq;
import com.pyhu.northernplanet.api.response.RoomGetRes;

public interface RoomService {

  /**
   * 방 아이디, 참가자 목록을 가지고 방에 대한 정보를 가져옴
   *
   * @param roomId, participants
   * @return {방 아이디, 이름, 설명, 시작시간, 종료시간, 활동상태, 참가자, 매니저 정보}
   */
  RoomGetRes getRoom(Long roomId, List<ParticipantDto> participants);

  /**
   * 방 아이디로 방이 있는지 여부 겁사
   *
   * @param roomId
   * @return {방 아이디, 이름, 설명, 시작시간, 활동상태, 매니저 정보}
   */
  Room getRoom(Long roomId);

  /**
   * 사용자 아이디로 방 목록 가져옴
   *
   * @param userId
   * @return 방 목록
   */
  List<RoomGetRes> getRoomListByUserId(Long userId);

  /**
   * 발표 방 생성
   *
   * @param registerInfo
   */
  void createRoom(RoomPostReq registerInfo);

  /**
   * 방 아이디로 방 정보 삭제
   *
   * @param roomId
   */
  void deleteRoom(Long roomId);

  /**
   * 방 정보 수정
   *
   * @param roomInfo
   * @return {방 아이디, 이름, 설명, 시작시간, 종료시간, 활동상태, 참가자, 매니저 정보}
   */
  RoomPutRes updateRoom(RoomPutReq roomInfo);
}
