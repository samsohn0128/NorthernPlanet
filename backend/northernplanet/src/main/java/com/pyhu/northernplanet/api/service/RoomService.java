package com.pyhu.northernplanet.api.service;


import com.pyhu.northernplanet.common.dto.ParticipantDto;
import java.util.List;
import com.pyhu.northernplanet.api.request.RoomPostReq;
import com.pyhu.northernplanet.api.response.RoomGetRes;
import com.pyhu.northernplanet.db.entity.Room;

public interface RoomService {

  /**
   * 방 아이디, 참가자 목록을 가지고 방에 대한 정보를 가져옴
   *
   * @param roomId, participants
   * @return {방 아이디, 이름, 설명, 시작시간, 종료시간, 활동상태, 참가자, 매니저 정보}
   */
  RoomGetRes getRoom(Long roomId, List<ParticipantDto> participants);

  /**
   * 사용자 아이디로 방 목록 가져옴
   *
   * @param userId
   * @return 방 목록
   */
  List<RoomGetRes> findbyuser(Long userId);

  /**
   * 발표 방 생성
   *
   * @param registerInfo
   */
  void createRoom(RoomPostReq registerInfo);
}
