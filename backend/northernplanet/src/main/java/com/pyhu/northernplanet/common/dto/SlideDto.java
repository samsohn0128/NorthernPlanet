package com.pyhu.northernplanet.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlideDto {

  private Long slideId;

  private Integer sequence;

  private String script;

  private byte[] slideFile;
}
