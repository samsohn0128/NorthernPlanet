package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.common.dto.UserOauthDto;
import com.pyhu.northernplanet.db.entity.Users;
import com.pyhu.northernplanet.db.repository.UserRepository;
import com.pyhu.northernplanet.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service("UserService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByOauthId(String oauthId) {
    Users user = userRepository.findByOauthId(oauthId)
        .orElseThrow(
            () -> new UsernameNotFoundException("User not found with oauthId : " + oauthId));

    log.info("loadUserByOauthId: {}", user);

    return UserPrincipal.create(user);
  }

  @Override
  public UserOauthDto getOauthUserByOauthId(String oauthId) {
    Users user = userRepository.findByOauthId(oauthId)
        .orElseThrow(
            () -> new UsernameNotFoundException("User not found with oauthId : " + oauthId));
    UserOauthDto userOauthDto = UserOauthDto.builder()
        .email(user.getEmail())
        .name(user.getName())
        .image(user.getImage())
        .oauthId(user.getOauthId())
        .build();
    log.info("getOauthUserByOauthId: {}", user);
    return userOauthDto;
  }
}
