package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.api.request.RoomOnLiveReq;
import com.pyhu.northernplanet.api.request.RoomRegisterPostReq;
import com.pyhu.northernplanet.api.request.RoomUpdateReq;
import com.pyhu.northernplanet.api.response.RoomGetRes;
import com.pyhu.northernplanet.db.entity.Rooms;
import java.util.List;


public interface RoomService {

  Rooms createRoom(RoomRegisterPostReq room);

  void deleteRoom(int roomId);

  Rooms getRoom(int roomId);

  //void deleteParticipants(String );
  List<RoomGetRes> findAll();

  Rooms updateRoom(RoomUpdateReq room);

  List<RoomGetRes> findbyuser(int userId);

  void setRoomOnLive(RoomOnLiveReq roomInfo);
}
