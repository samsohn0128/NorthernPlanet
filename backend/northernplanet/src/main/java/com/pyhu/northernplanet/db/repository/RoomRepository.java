package com.pyhu.northernplanet.db.repository;


import java.time.LocalDateTime;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.pyhu.northernplanet.db.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
  @Transactional
  int deleteAllByRoomId(Long roomId);
  @Modifying
  @Transactional
  @Query(value="UPDATE room SET end_time=?2 WHERE user_id=?1",nativeQuery=true)
  void leaveRoom(Long roomId, LocalDateTime now);
}
