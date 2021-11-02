package com.pyhu.northernplanet.api.response;

import com.pyhu.northernplanet.db.entity.Presentation;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ApiModel("SlideResponse")
@AllArgsConstructor
@NoArgsConstructor
public class SlideRes {

  private Long slideId;

  private String saveName;

  private String originalName;

  private String directory;

  private String script;

  private Integer sequence;

}
