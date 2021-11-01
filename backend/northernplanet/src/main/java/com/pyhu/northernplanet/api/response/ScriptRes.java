package com.pyhu.northernplanet.api.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ApiModel("ScriptResponse")
@AllArgsConstructor
@NoArgsConstructor
public class ScriptRes {

  private Long slideId;

  private String script;

}
