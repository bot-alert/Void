package com.example.voidapp.controller.user;

import com.example.voidapp.dto.UserDto;
import com.example.voidapp.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserRestController {
  private final UserEntityService userEntityService;

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getById(@PathVariable String id) {
    return ResponseEntity.ok(userEntityService.getById(id));
  }

  @GetMapping
  public ResponseEntity<List<UserDto>> getAll() {
    return ResponseEntity.ok(userEntityService.getAll());
  }
}
