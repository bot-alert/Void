package com.example.ecommerce.configuration;

import com.example.ecommerce.emus.RoleEnum;
import com.example.ecommerce.model.Role;
import com.example.ecommerce.model.UserEntity;
import com.example.ecommerce.repository.RoleRepository;
import com.example.ecommerce.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserEntityRepository userEntityRepository;

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

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
    if (Boolean.FALSE.equals(userEntityRepository.existsByUsername("admin"))) {
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
