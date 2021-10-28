package com.pyhu.northernplanet.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Data;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class Participant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long participantId;

  @ManyToOne
  @JoinColumn(name = "code_id")
  Code code;

  @ManyToOne
  @JoinColumn(name = "user_id")
  User user;

  @ManyToOne
  @JoinColumn(name = "room_id")
  Room room;

}
