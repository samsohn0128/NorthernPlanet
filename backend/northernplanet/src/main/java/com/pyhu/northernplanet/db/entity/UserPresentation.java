package com.pyhu.northernplanet.db.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserPresentation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_presentation_id")
  int userPresentationId;

  @ManyToOne
  @JoinColumn(name = "presentation_id")
  Presentations presentations;

  @ManyToOne
  @JoinColumn(name = "user_id")
  Users users;
}
