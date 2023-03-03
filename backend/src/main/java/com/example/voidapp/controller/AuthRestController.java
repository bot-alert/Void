package com.example.voidapp.controller;

import com.example.voidapp.dto.AuthResponseDto;
import com.example.voidapp.dto.LoginDto;
import com.example.voidapp.dto.RegisterDto;
import com.example.voidapp.handler.CostumeLogoutHandler;
import com.example.voidapp.security.JWTGenerator;
import com.example.voidapp.service.UserEntityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
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
    logoutHandler.logout(httpServletRequest);
    return ResponseEntity.ok("LOGGED OUT");
  }
}
