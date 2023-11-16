package com.ceos.daangnmarket.common.config;

import com.ceos.daangnmarket.common.jwt.JwtAccessDeniedHandler;
import com.ceos.daangnmarket.common.jwt.JwtAuthenticationEntryPoint;
import com.ceos.daangnmarket.common.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final TokenProvider tokenProvider;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      .csrf().disable()

      .exceptionHandling()
      .authenticationEntryPoint(jwtAuthenticationEntryPoint)
      .accessDeniedHandler(jwtAccessDeniedHandler)

      .and()
      .headers()
      .frameOptions()
      .sameOrigin()

      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

      .and()
      .authorizeHttpRequests()
      .requestMatchers("/api/auth/signin").permitAll()
      .requestMatchers("/api/auth/signup").permitAll()
      .anyRequest().authenticated()

      .and()
      .apply(new JwtSecurityConfig(tokenProvider));

    return httpSecurity.build();
  }

}
