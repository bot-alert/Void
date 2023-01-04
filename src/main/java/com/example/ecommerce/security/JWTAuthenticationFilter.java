package com.example.ecommerce.security;

import com.example.ecommerce.constant.ResponseConstant;
import com.example.ecommerce.constant.SecurityConstant;
import com.example.ecommerce.expection.UserException;
import com.example.ecommerce.service.UserEntityService;
import com.example.ecommerce.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Configuration
public class JWTAuthenticationFilter extends OncePerRequestFilter {
  @Autowired
  private JWTGenerator jwtGenerator;
  @Autowired
  private UserEntityService userEntityService;


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String token = jwtGenerator.getJWTFromRequest(request);

    Cookie[] cookies = request.getCookies();
    if (Objects.nonNull(cookies)) {
      for (Cookie cookie : cookies) {
        if (SecurityConstant.OAUTH2_TOKEN.equals(cookie.getName()) && jwtGenerator.validToken(cookie.getValue()) && !JwtUtil.isBlackListToken(token)) {
          String email = jwtGenerator.getUsernameFromJWT(cookie.getValue());
          setAuthentication(request, email);
        }
      }
    }
    if (StringUtils.hasText(token) && jwtGenerator.validToken(token) && !JwtUtil.isBlackListToken(token)) {
      String username = jwtGenerator.getUsernameFromJWT(token);
      setAuthentication(request, username);
    }
    filterChain.doFilter(request, response);
  }

  private void setAuthentication(HttpServletRequest request, String email) {
    UserDetails userDetails = userEntityService.loadUserByUsername(email);
    if (Objects.isNull(userDetails)) {
      throw new UserException(ResponseConstant.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
  }

}
