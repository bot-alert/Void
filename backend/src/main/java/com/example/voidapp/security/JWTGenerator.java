package com.example.voidapp.security;

import com.example.voidapp.emus.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class JWTGenerator {

  private final JwtEncoder encoder;

  public String generateToken(Authentication authentication) {
    Instant now = Instant.now();
    String role = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst()
            .orElseThrow(() -> new JwtException("Cannot find role for the user."));
    JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
            .issuedAt(now)
            .subject(authentication.getName())
            .claim("role", role)
            .expiresAt(now.plus(2, ChronoUnit.HOURS))
            .build();
    return this.encoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
  }

  public String generateTokenForOAuthUser(String email) {
    Instant now = Instant.now();
    JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
            .issuedAt(now)
            .subject(email)
            .claim("role", RoleEnum.ROLE_USER.name())
            .expiresAt(now.plus(2, ChronoUnit.HOURS))
            .build();
    return this.encoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
  }
}
