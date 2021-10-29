package com.pyhu.northernplanet.api.response;

import com.pyhu.northernplanet.common.dto.PresentationDto;
import java.util.List;
import lombok.Data;

@Data
public class PresentationListGetRes {

  private List<PresentationDto> presentationList;
}
