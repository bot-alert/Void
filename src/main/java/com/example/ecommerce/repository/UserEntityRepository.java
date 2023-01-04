package com.example.ecommerce.repository;

import com.example.ecommerce.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByUsername(String username);
  Optional<UserEntity> findByEmail(String username);

  boolean existsByUsername(String username);

  boolean existsByEmailAndEmailIsNotNull(String email);
}
