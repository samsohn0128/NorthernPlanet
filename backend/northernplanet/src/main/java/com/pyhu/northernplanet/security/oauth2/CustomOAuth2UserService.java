package com.pyhu.northernplanet.security.oauth2;

import com.pyhu.northernplanet.common.exception.OAuth2AuthenticationProcessingException;
import com.pyhu.northernplanet.db.entity.Users;
import com.pyhu.northernplanet.db.repository.UserRepository;
import com.pyhu.northernplanet.security.UserPrincipal;
import com.pyhu.northernplanet.security.oauth2.user.OAuth2UserInfo;
import com.pyhu.northernplanet.security.oauth2.user.OAuth2UserInfoFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final UserRepository userRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest)
      throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

    try {
      return processOAuth2User(oAuth2UserRequest, oAuth2User);
    } catch (AuthenticationException ex) {
      throw ex;
    } catch (Exception ex) {
      // Throwing an instance of AuthenticationException will trigger the
      // OAuth2AuthenticationFailureHandler
      throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
    }
  }

  private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
    OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
        oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
    if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
      throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
    }

    //Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
    Optional<Users> userOptional = userRepository.findByOauthId(oAuth2UserInfo.getId());
    Users user;
    if (userOptional.isPresent()) {
      user = userOptional.get();
      if (!user.getOauthType().equals(
          oAuth2UserRequest.getClientRegistration().getRegistrationId())) {
        throw new OAuth2AuthenticationProcessingException(
            "Looks like you're signed up with " + user.getOauthType() + " account. Please use your "
                + user.getOauthType() + " account to login.");
      }
      user = updateExistingUser(user, oAuth2UserInfo);
    } else {
      user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
    }

    return UserPrincipal.create(user, oAuth2User.getAttributes());
  }

  private Users registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
    log.info("Register new user {}", oAuth2UserInfo.getEmail());
    
    Users user = new Users();

    user.setOauthType(oAuth2UserRequest.getClientRegistration().getRegistrationId());
    user.setOauthId(oAuth2UserInfo.getId());
    user.setName(oAuth2UserInfo.getName());
    user.setEmail(oAuth2UserInfo.getEmail());
    user.setImage(oAuth2UserInfo.getImageUrl());
    return userRepository.save(user);
  }

  private Users updateExistingUser(Users existingUser, OAuth2UserInfo oAuth2UserInfo) {
    log.info("update user {}", oAuth2UserInfo.getEmail());
    
    existingUser.setName(oAuth2UserInfo.getName());
    existingUser.setImage(oAuth2UserInfo.getImageUrl());
    return userRepository.save(existingUser);
  }

}