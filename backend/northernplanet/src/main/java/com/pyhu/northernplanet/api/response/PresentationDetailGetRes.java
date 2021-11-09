package com.pyhu.northernplanet.api.response;

import com.pyhu.northernplanet.common.dto.SlideDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PresentationDetailGetRes {

  private Long presentationId;

  private String presentationName;

  private Integer size;

  private LocalDateTime uploadTime;

  private List<SlideDto> slideList;

  private Integer effect;
}
