package com.example.voidapp.service;

import com.example.voidapp.common.service.CommonService;
import com.example.voidapp.dto.RegisterDto;
import com.example.voidapp.dto.UserDto;
import com.example.voidapp.model.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserEntityService extends CommonService<UserDto> {
  void save(RegisterDto registerDto);


  UserDetails loadUserByUsername(String username);

  void login(String email);

  void logout(String email);
}
