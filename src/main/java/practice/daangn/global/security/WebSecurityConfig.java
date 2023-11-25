package practice.daangn.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import practice.daangn.global.jwt.JwtAuthenticationFilter;
import practice.daangn.global.jwt.TokenProvider;

import java.time.Duration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;
    private final RedisTemplate redisTemplate;


    @Bean //비밀번호 암호화 처리하는 메서드 제공
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())// token을 사용하는 방식이기 때문에 csrf를 disable로 설정
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .authorizeRequests() // 요청에 대한 접근 권한을 설정하는 메서드
                .requestMatchers(HttpMethod.POST, "/api/v1/users/signUp").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/users/signIn").permitAll()
                .anyRequest().permitAll();

        http
                .formLogin(formLogin -> formLogin.disable()) // 사용자 지정 로그인 로직 구현
                .httpBasic(httpBasic -> httpBasic.disable()); // http 기본 인증 비활성화


        http.addFilterBefore(new JwtAuthenticationFilter(tokenProvider, redisTemplate), UsernamePasswordAuthenticationFilter.class);

        // JwtExceptionHandlerFilter 추가



        // enable Mysql-console
        http.headers(headers ->
                headers.frameOptions(options ->
                        options.sameOrigin()
                )
        );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(Duration.ofSeconds(3600));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
