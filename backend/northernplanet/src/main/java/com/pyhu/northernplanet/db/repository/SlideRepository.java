package com.pyhu.northernplanet.db.repository;

import com.pyhu.northernplanet.db.entity.Slides;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlideRepository extends JpaRepository<Slides, Integer> {

  // @Query(value="select g.user_id from Presentations p inner join
  // Rooms_Presentations g where g.room_id=?1",nativeQuery=true)
//    List<FileRes> findAllBygoupid(int roomId);
  //List<Slides> findByroomspresentations_GroupId(int presentationId);
  List<Slides> findBypresentations_PresentationId(int presentationId);

}
