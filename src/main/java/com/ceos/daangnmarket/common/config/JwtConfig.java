//package com.ceos.daangnmarket.common.config;
//
//import com.ceos.daangnmarket.common.jwt.JwtProperties;
//import com.ceos.daangnmarket.common.jwt.TokenProvider;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableConfigurationProperties(JwtProperties.class)
//public class JwtConfig {
//
//  @Bean(name = "tokenProvider")
//  public TokenProvider tokenProvider(JwtProperties jwtProperties) {
//    return new TokenProvider(jwtProperties.getSecret(), jwtProperties.getAccessTokenValidityInSeconds());
//  }
//}