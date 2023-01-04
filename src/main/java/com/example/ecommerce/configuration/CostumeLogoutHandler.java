package com.example.ecommerce.configuration;

import com.example.ecommerce.constant.SecurityConstant;
import com.example.ecommerce.security.JWTGenerator;
import com.example.ecommerce.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@RequiredArgsConstructor
public class CostumeLogoutHandler {
  private final JWTGenerator jwtGenerator;

  public void logout(HttpServletRequest request, HttpServletResponse response) {
    removeOauth2Cookie(response);
    String token = jwtGenerator.getJWTFromRequest(request);
    if (StringUtils.hasText(token) && jwtGenerator.validToken(token)) {
      JwtUtil.addBlackListToken(jwtGenerator.getUsernameFromJWT(token), token);
    }
  }

  private static void removeOauth2Cookie(HttpServletResponse response) {
    Cookie cookie = new Cookie(SecurityConstant.OAUTH2_TOKEN, "");
    cookie.setMaxAge(0);
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    response.addCookie(cookie);
  }


}
