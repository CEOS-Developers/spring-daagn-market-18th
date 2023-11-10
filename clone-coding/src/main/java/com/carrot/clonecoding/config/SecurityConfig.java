package com.carrot.clonecoding.config;

import com.carrot.clonecoding.jwt.JwtAccessDeniedHandler;
import com.carrot.clonecoding.jwt.JwtAuthenticationEntryPoint;
import com.carrot.clonecoding.jwt.JwtSecurityConfig;
import com.carrot.clonecoding.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
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
                .csrf(AbstractHttpConfigurer::disable
                )
                .headers((headerConfig) ->
                        headerConfig.frameOptions(FrameOptionsConfig::disable)
                )
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
//                                .requestMatchers("/api/hello").permitAll()
                                .requestMatchers("/api/auth").permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                ).apply(new JwtSecurityConfig(tokenProvider));

//        httpSecurity.csrf().disable().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .accessDeniedHandler(jwtAccessDeniedHandler).and()
//                .headers()
//                .frameOptions()
//                .sameOrigin()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers("/api/hello").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .apply(new JwtSecurityConfig(tokenProvider));

        return httpSecurity.build();
    }

}