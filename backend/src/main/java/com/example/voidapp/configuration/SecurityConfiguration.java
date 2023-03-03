package com.example.voidapp.configuration;

import com.example.voidapp.handler.OAuth2SuccessHandler;
import com.example.voidapp.security.ApplicationAuthenticationFilter;
import com.example.voidapp.security.JwtAuthEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
  private final JwtAuthEntryPoint authEntryPoint;
  private final OAuth2SuccessHandler oAuth2SuccessHandler;
  private final ApplicationAuthenticationFilter applicationAuthenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf()
            .disable()
            .exceptionHandling()
            .authenticationEntryPoint(authEntryPoint)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests(
                    authorizeRequests -> {
                      authorizeRequests.requestMatchers("/api/auth/**", "/", "login").permitAll()
                              .requestMatchers("/oauth2/authorization/google").permitAll()
                              .requestMatchers("/success").permitAll()
                              .anyRequest()
                              .authenticated();

                    }
            )
            .logout()
            .logoutSuccessUrl("/api/auth/logout")
            .and()
            .oauth2Login()
            .successHandler(oAuth2SuccessHandler);
    http.addFilterBefore(applicationAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

}


