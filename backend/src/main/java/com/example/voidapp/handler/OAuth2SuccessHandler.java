package com.example.voidapp.handler;

import com.example.voidapp.constant.SecurityConstant;
import com.example.voidapp.emus.RoleEnum;
import com.example.voidapp.model.UserEntity;
import com.example.voidapp.repository.RoleRepository;
import com.example.voidapp.repository.UserEntityRepository;
import com.example.voidapp.security.JWTGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Order
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
  private final UserEntityRepository userEntityRepository;
  private final RoleRepository roleRepository;
  private final JWTGenerator jwtGenerator;
  @Value("${spring.google.redirect-url}")
  private String redirectUrl;


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws ServletException, IOException {
    DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
    OidcUserInfo userInfo = new OidcUserInfo(oauthUser.getClaims());
    persistNewUserToDatabase(userInfo);
    generateCookie(response, userInfo.getEmail());
    System.out.println("HERe");
    response.sendRedirect(redirectUrl);
    response.setStatus(302);
    super.onAuthenticationSuccess(request, response, authentication);
  }

  private void persistNewUserToDatabase(OidcUserInfo userInfo) {
    if (!userEntityRepository.existsByEmailAndEmailIsNotNull(userInfo.getEmail())) {
      UserEntity user = new UserEntity();
      user.setEmail(userInfo.getEmail());
      user.setPassword(UUID.randomUUID().toString());
      user.setUsername(userInfo.getFullName());
      user.setRole(roleRepository.findByRoleEnum(RoleEnum.ROLE_USER).orElseThrow(RuntimeException::new));
      userEntityRepository.save(user);
    }
  }

  private void generateCookie(HttpServletResponse response, String email) {
    Cookie cookie = new Cookie(SecurityConstant.OAUTH2_TOKEN, jwtGenerator.generateTokenForOAuthUser(email));
    cookie.setMaxAge(SecurityConstant.COOKIE_EXPIRATION);
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    response.addCookie(cookie);
  }
}
