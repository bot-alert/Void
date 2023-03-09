package com.example.voidapp.repository;

import com.example.voidapp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmail(String username);
  Optional<UserEntity> findByUuid(String uuid);

  boolean existsByEmailAndEmailIsNotNull(String email);
}
