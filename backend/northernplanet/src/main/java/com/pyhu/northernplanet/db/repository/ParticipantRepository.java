package com.pyhu.northernplanet.db.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pyhu.northernplanet.db.entity.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

  @Transactional
  List<Participant> findByrooms_roomId(Long room_id);

  @Transactional
  List<Participant> findByusers_userId(Long user_id);

  @Transactional
  int deleteAllByRooms_RoomId(Long roomId);
}
