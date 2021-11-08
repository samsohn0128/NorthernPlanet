package com.pyhu.northernplanet.common.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PresentationDto {

  private Long presentationId;

  private String presentationName;

  private Integer size;

  private LocalDateTime uploadTime;

  private List<SlideDto> slideList;

  private String contentType;

  private byte[] thumbnail;
}
