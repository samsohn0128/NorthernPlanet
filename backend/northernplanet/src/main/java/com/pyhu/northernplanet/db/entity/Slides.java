package com.pyhu.northernplanet.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import javax.swing.text.html.HTML;
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
public class Slides {

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
  int sequence;

  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "presentation_id")
  Presentations presentations;
}
