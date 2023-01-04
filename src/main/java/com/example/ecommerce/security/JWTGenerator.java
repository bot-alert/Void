package com.example.ecommerce.security;

import com.example.ecommerce.constant.SecurityConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JWTGenerator {
  public String generateToken(Authentication authentication) {
    String username = authentication.getName();
    Date currentDate = new Date();
    Date expireDate = new Date(currentDate.getTime() + SecurityConstant.JWR_EXPIRATION);
    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(expireDate)
            .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWR_SECRETE)
            .compact();
  }

  public String generateTokenForOAuth2(String email) {
    Date currentDate = new Date();
    Date expireDate = new Date(currentDate.getTime() + SecurityConstant.JWR_EXPIRATION);
    return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(expireDate)
            .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWR_SECRETE)
            .compact();
  }

  public String getUsernameFromJWT(String token) {
    Claims claims = Jwts.parser()
            .setSigningKey(SecurityConstant.JWR_SECRETE)
            .parseClaimsJws(token)
            .getBody();
    return claims.getSubject();
  }

  public boolean validToken(String token) {
    try {
      Jwts.parser().setSigningKey(SecurityConstant.JWR_SECRETE).parseClaimsJws(token);
      return true;
    } catch (Exception exception) {
      throw new AuthenticationCredentialsNotFoundException("JWT token was expired or incorrect");
    }
  }
  public  String getJWTFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
