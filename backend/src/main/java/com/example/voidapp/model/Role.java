package com.example.voidapp.model;

import com.example.voidapp.emus.RoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;
  @Enumerated(EnumType.STRING)
  private RoleEnum roleEnum;
  @CreatedDate
  private Long createdAt;
  @LastModifiedDate
  private Long updatedAt;

  @PrePersist
  protected void prePersist() {
    if (this.createdAt == null) this.createdAt = new Date().getTime();
    if (this.updatedAt == null) this.updatedAt = new Date().getTime();
  }

  @PreUpdate
  protected void preUpdate() {
    this.updatedAt = new Date().getTime();
  }
}
