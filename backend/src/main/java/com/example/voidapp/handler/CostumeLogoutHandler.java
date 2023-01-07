package com.example.voidapp.handler;

import com.example.voidapp.constant.SecurityConstant;
import com.example.voidapp.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;


@Configuration
@RequiredArgsConstructor
public class CostumeLogoutHandler {
  private final JwtUtil jwtUtil;

  public void logout(HttpServletRequest request, HttpServletResponse response) {
    removeOauth2Token(request, response);
    String token = jwtUtil.getJWTFromRequest(request);
    if (StringUtils.hasText(token) && jwtUtil.isValidToken(token)) {
      jwtUtil.addBlackListToken(token);
    }
  }

  private void removeOauth2Token(HttpServletRequest request, HttpServletResponse response) {
    Cookie oauth2Cookies = jwtUtil.getOAuthCookies(request.getCookies());
    if (null != oauth2Cookies) {
      jwtUtil.addBlackListToken(oauth2Cookies.getValue());
    }
    Cookie cookie = new Cookie(SecurityConstant.OAUTH2_TOKEN, "");
    cookie.setMaxAge(0);
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    response.addCookie(cookie);
  }


}
