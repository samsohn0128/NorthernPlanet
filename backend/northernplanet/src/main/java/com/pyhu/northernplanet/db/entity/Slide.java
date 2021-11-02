package com.pyhu.northernplanet.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Slide {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "slide_id")
  private Long slideId;

  @Column(name = "save_name")
  private String saveName;

  @Column(name = "original_name")
  private String originalName;

  private String directory;

  @Column(columnDefinition = "TEXT", name = "script")
  private String script;

  private Integer sequence;

  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "presentation_id")
  private Presentation presentation;
}
