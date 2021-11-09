package com.pyhu.northernplanet.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SlideDto {

  private Long slideId;

  private Integer sequence;

  private String script;

  private byte[] slideFile;

  private Integer effect;
}
