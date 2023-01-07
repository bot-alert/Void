package com.example.voidapp;

import com.example.voidapp.configuration.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableConfigurationProperties(RsaKeyProperties.class)
@EnableAsync
@SpringBootApplication
public class VoidApplication {

  public static void main(String[] args) {
    SpringApplication.run(VoidApplication.class, args);
  }

}

