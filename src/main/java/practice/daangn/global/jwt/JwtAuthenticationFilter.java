package practice.daangn.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final RedisTemplate redisTemplate;

    public JwtAuthenticationFilter(TokenProvider provider, RedisTemplate redisTemplate) {
        this.tokenProvider = provider;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 요청이 들어올 때마다 실행되는 메서드로,
     * 요청의 헤더에서 JWT 토큰을 추출하여 유효성을 검사하고,
     * 유효한 토큰이면 인증 정보를 추출하여 현재 보안 컨텍스트에 설정
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(request); // 요청 본문을 캐시에 저장
        String token = parseBearerToken(cachingRequestWrapper); //request 헤더에서 토큰을 가져옴

        if (token != null && tokenProvider.validateToken(token)) {
            //유효한 토큰이면 TokenProvider를 통해 Authentication 객체를 생성
            Authentication authentication = tokenProvider.getAuthentication(token);
            // 현재 스레드의 Security Context에 인증 정보를 저장 -> 해당 요청을 처리하는 동안 인증된 사용자로서 권한이 부여
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(cachingRequestWrapper,response);
    }


    private String parseBearerToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }

}
