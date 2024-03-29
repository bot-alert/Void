package com.example.voidapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterDto {
  private String username;
  private String password;
  private String email;
  private String image;
}
