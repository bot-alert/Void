package com.example.ecommerce.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
  private static Map<String, String> blackListToken = new HashMap<>();

  public static boolean isBlackListToken(String token) {
    return blackListToken.containsValue(token);
  }

  public static void addBlackListToken(String email, String token) {
    blackListToken.put(email, token);
  }

}
