package com.example.ecommerce.constant;

public class SecurityConstant {
  private SecurityConstant() {
  }

  public static final long JWR_EXPIRATION = 1000 * 60 * 60 * 24 * 10L;
  public static final int COOKIE_EXPIRATION = 86400 * 10;
  public static final String JWR_SECRETE = "MY_JWT_SECRETE";//TODO:MAKE SECURE SECRETE
  public static final String OAUTH2_TOKEN = "Oauth2";
}
