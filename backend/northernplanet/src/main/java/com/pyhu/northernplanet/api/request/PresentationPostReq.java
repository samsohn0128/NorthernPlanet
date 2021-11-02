package com.pyhu.northernplanet.api.request;

import java.util.List;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PresentationPostReq {

  private Long userId;

  private List<MultipartFile> slides;
}
