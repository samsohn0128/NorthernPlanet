package com.pyhu.northernplanet.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pyhu.northernplanet.db.entity.Presentation;

public interface PresentationRepository extends JpaRepository<Presentation, Long> {

}
