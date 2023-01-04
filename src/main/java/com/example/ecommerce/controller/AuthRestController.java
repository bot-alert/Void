package com.example.ecommerce.controller;

import com.example.ecommerce.configuration.CostumeLogoutHandler;
import com.example.ecommerce.dto.AuthResponseDto;
import com.example.ecommerce.dto.LoginDto;
import com.example.ecommerce.dto.RegisterDto;
import com.example.ecommerce.security.JWTGenerator;
import com.example.ecommerce.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthRestController {
  private final UserEntityService userEntityService;
  private final AuthenticationManager authenticationManager;
  private final JWTGenerator jwtGenerator;
  private final CostumeLogoutHandler logoutHandler;

  @PostMapping("/register")
  public ResponseEntity<String> save(@RequestBody @Valid RegisterDto registerDto) {
    userEntityService.save(registerDto);
    return ResponseEntity.ok("User registered !");
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid LoginDto loginDto) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtGenerator.generateToken(authentication);
    return ResponseEntity.ok(new AuthResponseDto(token));
  }

  @GetMapping("/logout")
  public ResponseEntity<String> login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    System.out.println("logout controller");
    logoutHandler.logout(httpServletRequest, httpServletResponse);
    return ResponseEntity.ok("LOGGED OUT");
  }
}
