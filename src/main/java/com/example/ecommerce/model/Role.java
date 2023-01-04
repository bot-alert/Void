package com.example.ecommerce.model;

import com.example.ecommerce.emus.RoleEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
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
