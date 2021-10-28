package com.pyhu.northernplanet.db.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Room {

  @Column(name = "room_id")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long roomId;

  String name;

  String description;

  @Column(name = "startTime", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  LocalDateTime startTime;

  @Column(name = "endTime", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  LocalDateTime endTime;

  @Column(name = "onLive", columnDefinition = "BOOLEAN DEFAULT FALSE")
  Boolean onLive;

  @OneToMany(mappedBy = "rooms", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Participant> participants;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  User user;

}
