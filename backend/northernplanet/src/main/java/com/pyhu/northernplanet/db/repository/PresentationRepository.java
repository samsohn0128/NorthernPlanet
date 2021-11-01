package com.pyhu.northernplanet.db.repository;

import com.pyhu.northernplanet.db.entity.Presentation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresentationRepository extends JpaRepository<Presentation, Long> {

  Optional<List<Presentation>> findByUser_userId(Long userId);
}
