package com.pyhu.northernplanet.db.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class Presentation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "presentation_id")
  Long presentationId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  User user;

  String name;

  int size;

  @Column(name = "upload_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  LocalDateTime uploadTime;

  @OneToMany(mappedBy = "presentation", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Slide> slides;
}
