package com.ceos18.dangn.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${jwt.secretKey}")
    private String secretKey;
    SecretKey key;

    @Value("${jwt.tokenValidTime}")
    private long tokenValidTime;

    @Value("${jwt.refreshValidTime}")
    private long refreshValidTime;

    private final CustomUserDetailsService userDetailsService;

    // 객체 초기화, secretKey -> Base64로 인코딩
    @PostConstruct //스프링이 빈 생성 후, 자동으로 호출
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // JWT Token 생성
    public String createAccessToken(Long userId, String phone, String role) {
        Claims claims = Jwts.claims().setSubject(userId.toString()); // JWT payload 에 저장되는 정보단위
        claims.put("phone", phone); //일종의 Map으로 key-value 쌍으로 저장
        claims.put("role", role);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

//    public String refreshToken()

    //Header에서 TOKEN 꺼내기
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    //Token 유효성 - 만료 여부 검증
    public boolean validateToken(String token) {
        //Token 만료 시간 또는 null 반환
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        boolean isExpired = expiration.before(new Date());

        return !isExpired;
    }

    //TOKEN에서 사용자 정보 꺼내기
    public String getUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //권한 부여
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
