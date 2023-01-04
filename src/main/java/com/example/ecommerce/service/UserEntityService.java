package com.example.ecommerce.service;

import com.example.ecommerce.dto.RegisterDto;
import com.example.ecommerce.model.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserEntityService {
  void save(RegisterDto registerDto);

  UserEntity getById(long id);

  UserDetails loadUserByUsername(String username);
}
