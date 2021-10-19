package com.pyhu.northernplanet.api.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ApiModel("FileRequest")
public class FileReq {

	@ApiModelProperty
	int room_id;
	@ApiModelProperty
	int user_id;
	@ApiModelProperty
	List<MultipartFile>files;
}
