package com.example.voidapp.service.impl;

import com.example.voidapp.constant.ResponseConstant;
import com.example.voidapp.dto.RegisterDto;
import com.example.voidapp.emus.RoleEnum;
import com.example.voidapp.expection.UserException;
import com.example.voidapp.model.Role;
import com.example.voidapp.model.UserEntity;
import com.example.voidapp.repository.RoleRepository;
import com.example.voidapp.repository.UserEntityRepository;
import com.example.voidapp.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserEntityEntityServiceImpl implements UserEntityService, UserDetailsService {
  private final UserEntityRepository userEntityRepository;
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;


  @Override
  public void save(RegisterDto registerDto) {
    if (Boolean.TRUE.equals(userEntityRepository.existsByEmailAndEmailIsNotNull(registerDto.getEmail()))) {
      throw new UserException(ResponseConstant.EMAIL_ALREADY_TAKEN, HttpStatus.IM_USED);
    }
    UserEntity user = new UserEntity();
    modelMapper.map(registerDto, user);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(roleRepository.findByRoleEnum(RoleEnum.ROLE_USER).orElseThrow(() ->
            new UserException(ResponseConstant.USER_ROLE_NOT_FOUND, HttpStatus.NOT_FOUND)));
    userEntityRepository.save(user);
  }


  @Override
  public UserEntity getById(long id) {
    return userEntityRepository.findById(id).orElseThrow(() ->
            new UserException(ResponseConstant.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    UserEntity user = userEntityRepository.findByEmail(username).orElseThrow(() ->
            new UserException(ResponseConstant.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
    return new User(user.getEmail(), user.getPassword(), mapRoleToAuthority(user.getRole()));
  }


  private Collection<GrantedAuthority> mapRoleToAuthority(Role roles) {
    return Collections.singletonList(new SimpleGrantedAuthority(roles.getRoleEnum().name()));
  }
}
