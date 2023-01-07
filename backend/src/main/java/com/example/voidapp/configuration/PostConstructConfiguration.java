package com.example.voidapp.configuration;

import com.example.voidapp.emus.RoleEnum;
import com.example.voidapp.model.Role;
import com.example.voidapp.model.UserEntity;
import com.example.voidapp.repository.RoleRepository;
import com.example.voidapp.repository.UserEntityRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class PostConstructConfiguration {
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserEntityRepository userEntityRepository;


  @PostConstruct
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public void createRole() {
    Role adminRole = new Role();
    adminRole.setRoleEnum(RoleEnum.ROLE_ADMIN);
    Role userRole = new Role();
    userRole.setRoleEnum(RoleEnum.ROLE_USER);
    if (Boolean.FALSE.equals(roleRepository.existsByRoleEnum(RoleEnum.ROLE_ADMIN))) {
      roleRepository.save(adminRole);
    }
    if (Boolean.FALSE.equals(roleRepository.existsByRoleEnum(RoleEnum.ROLE_USER))) {
      roleRepository.save(userRole);
    }
  }

  @PostConstruct
  @Order
  public void createSuperUser() {
    if (Boolean.FALSE.equals(userEntityRepository.existsByEmailAndEmailIsNotNull("a@gmail.com"))) {
      UserEntity superUser = new UserEntity();
      Optional<Role> role = roleRepository.findByRoleEnum(RoleEnum.ROLE_ADMIN);
      if (role.isPresent()) {
        superUser.setEmail("a@gmail.com");
        superUser.setUsername("admin");
        superUser.setPassword(passwordEncoder.encode("admin"));
        superUser.setRole(role.get());
        userEntityRepository.save(superUser);
      }
    }
  }
}