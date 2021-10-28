package com.pyhu.northernplanet.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pyhu.northernplanet.db.entity.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

}
