package com.ceos18.springboot.global.config.jwt;

import com.ceos18.springboot.user.exception.ErrorCode;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.ceos18.springboot.user.exception.ExceptionManager.setErrorResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = tokenProvider.getAccessToken(request);
        String requestURI = request.getRequestURI();

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (StringUtils.isNotBlank(token) && tokenProvider.validateAccessToken(token)) {

            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            logger.info("Security Context에 " + authentication.getName() + "인증 정보를 저장했습니다, uri: " + requestURI);
        } else {
            logger.info("유효한 JWT 토큰이 없습니다, uri: " + requestURI);
            setErrorResponse(response, ErrorCode.INVALID_TOKEN);
        }

        filterChain.doFilter(request, response);
    }
}