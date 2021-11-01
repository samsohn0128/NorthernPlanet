package com.pyhu.northernplanet.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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

  @Column(name = "script")
  String script;

  Integer sequence;

  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "presentation_id")
  Presentation presentation;
}
