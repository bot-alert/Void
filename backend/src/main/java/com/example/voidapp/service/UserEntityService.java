package com.example.voidapp.service;

import com.example.voidapp.dto.RegisterDto;
import com.example.voidapp.model.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserEntityService {
  void save(RegisterDto registerDto);

  UserEntity getById(long id);

  UserDetails loadUserByUsername(String username);
}
