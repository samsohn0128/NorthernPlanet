package com.pyhu.northernplanet.db.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Data;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class User {

  @Column(name = "user_id")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long userId;

  String email;

  String name;

  String image;

  @Column(name = "oauth_id")
  String oauthId;

  @Column(name = "oauth_type")
  String oauthType;

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Participant> participants;

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Room> rooms;

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Presentation> presentations;
}
