package com.pyhu.northernplanet.api.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PresentationListGetRes {

  private Long presentationId;

  private String presentationName;

  private Integer size;

  private LocalDateTime uploadTime;
}
