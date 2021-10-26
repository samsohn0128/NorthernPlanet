package com.pyhu.northernplanet.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Participants {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long participantId;

  @ManyToOne
  @JoinColumn(name = "code_id")
  Code code;


  @ManyToOne
  @JoinColumn(name = "user_id")
  Users users;

  @ManyToOne
  @JoinColumn(name = "room_id")
  Rooms rooms;

}
