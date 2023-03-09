package com.example.voidapp.repository;

import com.example.voidapp.emus.RoleEnum;
import com.example.voidapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByRoleEnum(RoleEnum name);

  Boolean existsByRoleEnum(RoleEnum name);
}
