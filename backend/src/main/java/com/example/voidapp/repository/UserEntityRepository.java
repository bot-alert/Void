package com.example.voidapp.repository;

import com.example.voidapp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmail(String username);

  boolean existsByEmailAndEmailIsNotNull(String email);
}
