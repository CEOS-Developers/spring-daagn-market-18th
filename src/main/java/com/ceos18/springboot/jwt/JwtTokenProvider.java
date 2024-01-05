package com.ceos18.springboot.jwt;

import com.ceos18.springboot.security.PrincipalDetails;
import com.ceos18.springboot.security.PrincipalDetailsService;
import com.ceos18.springboot.common.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Date;

@Component
@Transactional(readOnly = true)
@Slf4j
@PropertySource("classpath:application.yml")
public class JwtTokenProvider {
    private static final String AUTHORITIES_KEY = "role";
    private static final String EMAIL_KEY = "email";
    private final Key signingKey;
    private final Long accessTokenValidTime;
    private final PrincipalDetailsService principalDetailsService;

    public JwtTokenProvider(
            PrincipalDetailsService principalDetailsService,
            @Value("${jwt.token.secret}") String secretKey,
            @Value("${jwt.token.access-token-validity-in-seconds}") Long accessTokenValidTime
    ) {
        this.principalDetailsService = principalDetailsService;
        this.accessTokenValidTime = accessTokenValidTime * 1000L;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰 생성
    public TokenDto createToken(String email, String authorities) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessTokenValidTime);

        String accessToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS512")
                .setSubject("access-token")
                .claim(EMAIL_KEY, email)
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();

        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 토큰입니다.");
            return false;
        } catch (ExpiredJwtException e) {   //만료만 된 토큰이어도 true
            log.info("만료된 JWT 토큰입니다.");
            return true;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            return false;
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            return false;
        }
    }

    // 토큰의 claim 추출
    private Claims parseClaims(String accessToken) {
        try {
            //올바른 토큰이면 true
            return Jwts.parserBuilder().setSigningKey(signingKey).build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            //만료 토큰이어도 토큰 정보 꺼내서 return
            return e.getClaims();
        }
    }

    // 토큰에서 이메일 정보 추출하고 사용자 정보 가져옴
    public Authentication getAuthentication(String token) {
        String email = parseClaims(token).get(EMAIL_KEY).toString();
        PrincipalDetails principalDetails = principalDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(principalDetails, "",
                principalDetails.getAuthorities());
    }

}
