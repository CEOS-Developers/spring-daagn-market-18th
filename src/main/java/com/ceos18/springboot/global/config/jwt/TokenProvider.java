package com.ceos18.springboot.global.config.jwt;

import com.ceos18.springboot.global.config.user.PrincipalDetails;
import com.ceos18.springboot.global.config.user.PrincipalDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

    @Value("${jwt.token.secret}")
    private String secret;

    private Key key;

    private Long expireTimeMs = 1000 * 60 * 60L; // 1시간

    private final PrincipalDetailsService principalDetailsService;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String getAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }

    public String createAccessToken(Long id, String email, Authentication authentication) {
        String authorities =
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","));

        Claims claims = Jwts.claims(); // 일종의 map
        claims.put("id", id);
        claims.put("email", email);
        claims.put("auth", authorities);
        claims.put("type", "access");

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(email)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(key)
                .compact()
                ;
    }

    // 토큰
    public String getTokenUserEmail(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return (String) claims.get("email");
    }

    public Authentication getAuthentication(String token) {
        PrincipalDetails principalDetails =
                (PrincipalDetails)
                        principalDetailsService.loadUserByUsername(getTokenUserEmail(token));

        return new UsernamePasswordAuthenticationToken(
                principalDetails, token, principalDetails.getAuthorities());
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info(e.toString());
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info(e.toString());
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info(e.toString());
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info(e.toString());
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}