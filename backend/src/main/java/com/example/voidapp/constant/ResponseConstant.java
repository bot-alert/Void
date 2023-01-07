package com.example.voidapp.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class ResponseConstant {
  public static final String USER_NOT_FOUND = "User not found";
  public static final String USER_NAME_ALREADY_TAKEN = "Username already taken";
  public static final String EMAIL_ALREADY_TAKEN = "Email already taken";
  public static final String USER_ROLE_NOT_FOUND = "User role not found";
}
