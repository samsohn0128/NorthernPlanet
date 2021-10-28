package com.pyhu.northernplanet.db.repository;

import com.pyhu.northernplanet.db.entity.Rooms;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Rooms, Integer> {

  Rooms findByRoomId(int roomId);

  @Transactional
  int deleteAllByRoomId(int roomId);

  @Modifying
  @Transactional
  @Query(value = "UPDATE rooms SET on_live=?1 WHERE room_id=?2", nativeQuery = true)
  void updateRoomOnLive(Boolean onLive, int room_id);
}
