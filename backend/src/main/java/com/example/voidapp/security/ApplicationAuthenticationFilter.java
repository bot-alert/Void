package com.example.voidapp.security;

import com.example.voidapp.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class ApplicationAuthenticationFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    log.info(request.getRequestURI());
    String token = jwtUtil.getJWTFromRequest(request);
    validateOAuth2Users(request);
    validateNormalUsers(request, token);
    filterChain.doFilter(request, response);
  }

  private void validateNormalUsers(HttpServletRequest request, String token) {
    if (StringUtils.hasText(token) && jwtUtil.isValidToken(token) && jwtUtil.isNonBlackListToken(token)) {
      String[] emailAndRole = jwtUtil.getEmailAndRoleFromJWT(token);
      setAuthentication(request, emailAndRole);
    }
  }

  private void validateOAuth2Users(HttpServletRequest request) {
    Cookie oauth2Cookies = jwtUtil.getOAuthCookies(request.getCookies());
    if (null != oauth2Cookies) {
      String[] emailAndRole = jwtUtil.getEmailAndRoleFromJWT(oauth2Cookies.getValue());
      setAuthentication(request, emailAndRole);
    }
  }

  private void setAuthentication(HttpServletRequest request, String[] emailAndRole) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(emailAndRole[0], null,
            List.of(new SimpleGrantedAuthority(emailAndRole[1])));
    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
  }
}
