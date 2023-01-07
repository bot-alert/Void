package com.example.voidapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class Cont {

  @GetMapping("/")
  public String sendMoney() {
    return "DONE";
  }

  @GetMapping("/private")
  public String privateData() {
    return "PRIVATE DATA";
  }

  @GetMapping("/failed")
  public String failed() {
    return "failed DATA";
  }

  @GetMapping("/logout")
  public String logout() {
    return "LOGGED OUT";
  }
}
