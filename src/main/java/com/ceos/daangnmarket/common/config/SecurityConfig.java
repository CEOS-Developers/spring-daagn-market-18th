//package com.ceos.daangnmarket.common.config;
//
//import com.ceos.daangnmarket.common.jwt.JwtAccessDeniedHandler;
//import com.ceos.daangnmarket.common.jwt.JwtAuthenticationEntryPoint;
//import com.ceos.daangnmarket.common.jwt.JwtAuthenticationFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.filter.CorsFilter;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class SecurityConfig {
//  private final CorsFilter corsFilter;
//  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
//
//  public SecurityConfig(
//    CorsFilter corsFilter,
//    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
//    JwtAccessDeniedHandler jwtAccessDeniedHandler
//  ) {
//    this.corsFilter = corsFilter;
//    this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
//    this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
//  }
//
//  @Bean
//  public SecurityFilterChain filterChain(HttpSecurity httpSecurity, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
//    httpSecurity
//      .csrf().disable()
//
//      .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
//
//      .exceptionHandling()
//      .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//      .accessDeniedHandler(jwtAccessDeniedHandler)
//
//      .and()
//      .headers()
//      .frameOptions()
//      .sameOrigin()
//
//      .and()
//      .sessionManagement()
//      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//      .and()
//      .authorizeHttpRequests()
//      .requestMatchers("/api/auth/signup").permitAll()
//      .requestMatchers("/api/auth/signin").permitAll()
//      .anyRequest().authenticated()
//
//      .and()
//      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//    return httpSecurity.build();
//  }
//}
