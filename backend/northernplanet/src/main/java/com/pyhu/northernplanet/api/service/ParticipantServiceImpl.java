package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.api.response.ParticipantGetRes;
import com.pyhu.northernplanet.db.entity.Participants;
import com.pyhu.northernplanet.db.repository.ParticipantRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("participantService")
public class ParticipantServiceImpl implements ParticipantService {
	@Autowired
	ParticipantRepository participantRepository;

	@Override
	public List<ParticipantGetRes> getParticipantByRoomId(int roomId) {
		List<Participants> participants = participantRepository.findByrooms_roomId(roomId);
		List<ParticipantGetRes> participantGetReqList = new ArrayList<>();

		for (Participants item : participants) {
			participantGetReqList.add(new ParticipantGetRes(item.getUsers().getName(), item.getUsers().getEmail(),
					item.getGroupcode(), item.getCode()));
		}
		return participantGetReqList;
	}

}
