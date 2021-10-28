package com.pyhu.northernplanet.db.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pyhu.northernplanet.db.entity.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

  @Transactional
  List<Participant> findByRoom_roomId(Long roomId);

  @Transactional
  List<Participant> findByUser_userId(Long userId);

  @Transactional
  int deleteAllByRoom_RoomId(Long roomId);
}
