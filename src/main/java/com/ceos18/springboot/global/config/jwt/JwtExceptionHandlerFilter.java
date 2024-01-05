package com.ceos18.springboot.global.config.jwt;


import com.ceos18.springboot.user.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.NoSuchElementException;

import static com.ceos18.springboot.user.exception.ExceptionManager.setErrorResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰입니다");
            setErrorResponse(response, ErrorCode.EXPIRED_TOKEN);

        } catch (JwtException | IllegalArgumentException e) {
            log.error("유효하지 않은 토큰이 입력되었습니다.");
            setErrorResponse(response, ErrorCode.INVALID_TOKEN);

        } catch (NoSuchElementException e) {
            log.error("사용자를 찾을 수 없습니다.");
            setErrorResponse(response, ErrorCode.USEREMAIL_NOT_FOUND);

        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("토큰을 추출할 수 없습니다.");
            setErrorResponse(response, ErrorCode.INVALID_TOKEN);

        } catch (NullPointerException e) {
            filterChain.doFilter(request, response);
        }
    }
}
