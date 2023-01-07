package com.example.voidapp.util;

import com.example.voidapp.constant.SecurityConstant;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtUtil {
  private final JwtDecoder decoder;
  private static Map<String, String> blackListToken = new HashMap<>();

  public boolean isNonBlackListToken(String token) {
    return !blackListToken.containsKey(token);
  }

  public void addBlackListToken(String token) {
    if (blackListToken.containsKey(token)) return;
    blackListToken.put(token, getEmailAndRoleFromJWT(token)[0]);
    removeExpiredBlackListToken();
  }

  public boolean isValidToken(String token) {
    try {
      Jwt jwt = decoder.decode(token);
      return !isTokenExpired(jwt.getExpiresAt());
    } catch (Exception exception) {
      return false;
    }
  }


  public boolean isTokenExpired(Instant expiresAt) {
    if (Objects.isNull(expiresAt)) return true;
    return !Instant.now().isBefore(expiresAt);
  }

  public String[] getEmailAndRoleFromJWT(String token) {
    Jwt jwt = decoder.decode(token);
    return new String[]{(String) jwt.getClaims().get("sub"), (String) jwt.getClaims().get("role")};
  }


  public String getJWTFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public Cookie getOAuthCookies(Cookie[] cookies) {
    if (cookies == null) return null;
    for (Cookie cookie : cookies) {
      if (SecurityConstant.OAUTH2_TOKEN.equals(cookie.getName()) && isValidToken(cookie.getValue()) && isNonBlackListToken(cookie.getValue())) {
        return cookie;
      }
    }
    return null;
  }

  @Async
  void removeExpiredBlackListToken() {
    blackListToken.entrySet().removeIf(entry -> isTokenExpired(decoder.decode(entry.getKey()).getExpiresAt()));
  }
}
