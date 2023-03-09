package com.example.voidapp.handler;

import com.example.voidapp.service.UserEntityService;
import com.example.voidapp.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;


@Configuration
@RequiredArgsConstructor
public class CostumeLogoutHandler {
  private final JwtUtil jwtUtil;
  private final UserEntityService userEntityService;

  public void logout(HttpServletRequest request) {
    String token = jwtUtil.getJWTFromRequest(request);
    if (StringUtils.hasText(token) && jwtUtil.isValidToken(token)) {
      jwtUtil.addBlackListToken(token);
      userEntityService.logout(jwtUtil.getEmailAndRoleFromJWT(token)[0]);
    }
  }
}
