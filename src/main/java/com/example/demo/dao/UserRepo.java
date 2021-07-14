package com.example.demo.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.User_entity;
@Component("jpa")
@Repository
public interface UserRepo extends JpaRepository<User_entity, Long>{

}
