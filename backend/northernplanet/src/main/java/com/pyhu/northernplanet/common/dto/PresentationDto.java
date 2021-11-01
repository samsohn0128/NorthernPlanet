package com.pyhu.northernplanet.common.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PresentationDto {

  private Long presentationId;

  private String presentationName;

  private Integer size;

  private LocalDateTime uploadTime;

  private List<SlideDto> slideList;
}
