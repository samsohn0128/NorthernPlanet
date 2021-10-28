package com.pyhu.northernplanet.common.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserOauthDto implements Serializable {

  private String email;
  private String name;
  private String image;
  private String oauthId;

}
