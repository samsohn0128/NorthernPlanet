package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.common.dto.UserOauthDto;
import org.apache.catalina.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

  /**
   * TokenAuthenticationFilter 만들 때 사용 UserDetails 반환.
   *
   * @param oauthId 소셜 로그인 key값
   * @return userName, password
   */
  UserDetails loadUserByOauthId(String oauthId);

  /**
   * TokenAuthenticationFilter 만들 때 사용 UserDetails 반환.
   *
   * @param email
   * @return userName, password
   */
  UserDetails loadUserByEmail(String email);

  /**
   * oauthUserId로 사용자 정보 UserOauthDto 반환.
   *
   * @param oauthId 사용자 OAuthId
   * @return { 사용자 아이디, 이메일, 사용자 이름, 프로필 사진, oAuthId }
   */
  UserOauthDto getOauthUserByOauthId(String oauthId);

  /**
   * oauthId 로 userId 반환
   *
   * @param oauthId
   * @return 사용자 아이디
   */
  Long getUserIdByOauthId(String oauthId);

  /**
   * email로 사용자 정보 가져오기
   *
   * @param email
   * @return { 사용자 아이디, 이메일, 사용자 이름, 프로필 사진, oAuthId }
   */
  UserOauthDto getUserByEmail(String email);

  /**
   * 사용자 아이디로 사용자 이름 변경
   *
   * @param userId, name
   */
  void updateUserName(Long userId, String name);

  /**
   * 사용자 아이디로 회원탈퇴
   *
   * @param userId
   */
  void deleteUser(Long userId);


}
