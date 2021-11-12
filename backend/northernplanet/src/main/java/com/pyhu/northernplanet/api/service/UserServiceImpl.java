package com.pyhu.northernplanet.api.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.pyhu.northernplanet.common.dto.UserOauthDto;
import com.pyhu.northernplanet.db.entity.User;
import com.pyhu.northernplanet.db.repository.UserRepository;
import com.pyhu.northernplanet.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("UserService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByOauthId(String oauthId) {
    User user = userRepository.findByOauthId(oauthId).orElseThrow(
        () -> new UsernameNotFoundException("User not found with oauthId : " + oauthId));

    log.info("loadUserByOauthId: {}", user);

    return UserPrincipal.create(user);
  }

  @Override
  public UserDetails loadUserByEmail(String email) {
    User user = userRepository.findByEmail(email).orElseThrow(
        () -> new UsernameNotFoundException("User not found with email : " + email));

    log.info("loadUserByOauthId: {}", user);

    return UserPrincipal.create(user);
  }

  @Override
  public UserOauthDto getOauthUserByOauthId(String oauthId) {
    User user = userRepository.findByOauthId(oauthId).orElseThrow(
        () -> new UsernameNotFoundException("User not found with oauthId : " + oauthId));
    UserOauthDto userOauthDto = UserOauthDto.builder()
        .userId(user.getUserId())
        .email(user.getEmail())
        .name(user.getName())
        .image(user.getImage())
        .oauthId(user.getOauthId()).build();
    log.info("getOauthUserByOauthId: {}", user);
    return userOauthDto;
  }

  @Override
  public Long getUserIdByOauthId(String oauthId) {
    Long userId = userRepository.findUserIdByOauthId(oauthId);
    return userId;
  }

  @Override
  public UserOauthDto getUserByEmail(String email) {
    User user = userRepository.findByEmail(email).orElseThrow(
        () -> new UsernameNotFoundException("User not found with Email : " + email));
    UserOauthDto userOauthDto = UserOauthDto.builder()
        .userId(user.getUserId())
        .email(user.getEmail())
        .name(user.getName())
        .image(user.getImage())
        .oauthId(user.getOauthId()).build();
    return userOauthDto;
  }

  @Override
  public void updateUserName(Long userId, String name) {
    User user = userRepository.findByUserId(userId).orElseThrow(
        () -> new UsernameNotFoundException("User not found with userId : " + userId));
    log.info("userId: {}, name: {}", userId, name);
    userRepository.updateUserNameByUserId(userId, name);
  }

  @Override
  public void deleteUser(Long userId) {
    User user = userRepository.findByUserId(userId).orElseThrow(
        () -> new UsernameNotFoundException("User not found with userId : " + userId));
    userRepository.deleteById(userId);
  }
}
