package com.ceos18.springboot.global.config;

import com.ceos18.springboot.global.config.jwt.JwtAuthenticationFilter;
import com.ceos18.springboot.global.config.jwt.JwtExceptionHandlerFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtExceptionHandlerFilter jwtExceptionHandlerFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .httpBasic(Customizer.withDefaults())
                .csrf((csrf) -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // jwt 사용하는 경우 쓴다
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .anyRequest().authenticated()

                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionHandlerFilter, JwtAuthenticationFilter.class);


//                .exceptionHandling(authenticationManager -> authenticationManager
//                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//                        .accessDeniedHandler(new CustomAccessDeniedHandler()))


        return http.build();
    }


}

