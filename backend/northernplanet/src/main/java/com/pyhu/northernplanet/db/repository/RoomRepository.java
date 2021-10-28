package com.pyhu.northernplanet.db.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pyhu.northernplanet.db.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
