package practice.daangn.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.io.Decoders;
import practice.daangn.global.inmemory.RedisDao;
import practice.daangn.global.security.CustomUserDetailsService;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {
    private Key key;
    private final CustomUserDetailsService customUserDetailsService;
    private final RedisDao redisDao;

    @Value("${jwt.token.key}")
    private String secret;

    // access 유효시간 1시간
    private final long accessTokenValidTime = 60 * 60 * 1000L;
    // refresh token 유효시간 7일
    private final long refreshTokenValidTime = 7 * 24 * 60 * 60 * 1000L;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT AccessToken 생성
    public String createAccessToken(String email, String role) {
        Claims claims = Jwts.claims().setSubject(email); // JWT payload 에 저장되는 정보단위
        claims.put("role", role); // 정보는 key/value 쌍으로 저장
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, this.key) // 비밀키로 서명
                .compact();
    }

    //JWT Refresh Token 생성
    public String createRefreshToken(String email){
        Date now = new Date();

        String refreshToken = Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, this.key)
                .compact();

        redisDao.setValues(email, refreshToken, getExpiration(refreshToken)); //redis에 key: email, value: refreshToken 저장
        return refreshToken;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    // token의 유효성 + 만료일자 확인
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date()); // 만료시간 안지났다면 true
        } catch (Exception e) {
            return false;
        }
    }

    // token의 만료일자 가져오기
    public Duration getExpiration(String accessToken) {
        Date expiration = Jwts.parser().setSigningKey(secret).parseClaimsJws(accessToken).getBody().getExpiration();
        return Duration.between(Instant.now(), expiration.toInstant()); // 현재 시간과 토큰의 만료 시간 사이의 Duration 계산
    }


}
