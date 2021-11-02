package com.pyhu.northernplanet.api.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data

public class PPTtoPngReq {
    private Long userId;
    private MultipartFile ppt;

}
