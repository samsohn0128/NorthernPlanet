package com.pyhu.northernplanet.db.repository;

import com.pyhu.northernplanet.db.entity.Slide;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlideRepository extends JpaRepository<Slide, Long> {

  Optional<List<Slide>> findByPresentation_presentationId(Long presentationId);
}
