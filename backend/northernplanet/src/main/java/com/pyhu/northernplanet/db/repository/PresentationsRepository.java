package com.pyhu.northernplanet.db.repository;
import com.pyhu.northernplanet.db.entity.Presentations;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
public interface PresentationsRepository extends JpaRepository<Presentations, Integer>{

}
