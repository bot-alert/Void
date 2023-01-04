package com.example.ecommerce.configuration;

import com.example.ecommerce.constant.SecurityConstant;
import com.example.ecommerce.emus.RoleEnum;
import com.example.ecommerce.model.UserEntity;
import com.example.ecommerce.repository.RoleRepository;
import com.example.ecommerce.repository.UserEntityRepository;
import com.example.ecommerce.security.JWTGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
@Order
@RequiredArgsConstructor
public class GoogleAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
  private final UserEntityRepository userEntityRepository;
  private final RoleRepository roleRepository;
  private final JWTGenerator jwtGenerator;


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws ServletException, IOException {
    DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
    String email = oauthUser.getAttribute("email");
    generateCookie(response, email);
    if (Boolean.FALSE.equals(userEntityRepository.existsByEmailAndEmailIsNotNull(email))) {
      UserEntity user = new UserEntity();
      user.setEmail(email);
      user.setPassword(UUID.randomUUID().toString());
      user.setUsername("temp");
      user.setRole(roleRepository.findByRoleEnum(RoleEnum.ROLE_USER).orElseThrow(RuntimeException::new));
      userEntityRepository.save(user);
    }
    super.onAuthenticationSuccess(request, response, authentication);
  }

  private void generateCookie(HttpServletResponse response, String email) {
    Cookie cookie = new Cookie(SecurityConstant.OAUTH2_TOKEN, jwtGenerator.generateTokenForOAuth2(email));
    cookie.setMaxAge(SecurityConstant.COOKIE_EXPIRATION);
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    response.addCookie(cookie);
  }
}
