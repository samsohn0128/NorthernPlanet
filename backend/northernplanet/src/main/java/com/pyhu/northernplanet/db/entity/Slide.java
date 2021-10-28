package com.pyhu.northernplanet.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@DynamicInsert
@DynamicUpdate
public class Slide {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "slide_id")
  Long slideId;

  @Column(name = "save_name")
  String saveName;

  @Column(name = "original_name")
  String originalName;

  String directory;

  @Column(name = "script_directory")
  String scriptDirectory;

  Integer sequence;

  @ManyToOne
  @JoinColumn(name = "presentation_id")
  Presentation presentation;
}
