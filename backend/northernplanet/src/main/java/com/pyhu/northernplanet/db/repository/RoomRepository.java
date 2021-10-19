package com.pyhu.northernplanet.db.repository;


import com.pyhu.northernplanet.db.entity.Rooms;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface RoomRepository extends JpaRepository<Rooms, Integer> {
	Rooms findByRoomId(int roomId);

	@Transactional
	int deleteAllByRoomId(int roomId);

	@Modifying
	@Transactional
	@Query(value="UPDATE rooms SET on_live=?1 WHERE room_id=?2",nativeQuery=true)
	void updateRoomOnLive(Boolean onLive, int room_id);
}