package com.example.voidapp.dto;

import com.example.voidapp.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
  private long id;
  private String username;
  private String email;
  private String image;
  private boolean active;
  private long lastSeen;
  private String uuid;
  private Role role;
}
