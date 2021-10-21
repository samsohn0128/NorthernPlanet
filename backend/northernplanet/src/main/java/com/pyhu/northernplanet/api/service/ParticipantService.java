package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.api.response.ParticipantGetRes;
import java.util.List;

public interface ParticipantService {

  List<ParticipantGetRes> getParticipantByRoomId(int roomId);

}
