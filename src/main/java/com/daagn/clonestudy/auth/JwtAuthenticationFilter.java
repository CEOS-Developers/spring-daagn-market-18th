package com.daagn.clonestudy.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            String token = jwtTokenProvider.resolveToken((HttpServletRequest)request);

            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            setErrorResponse(response, e.getMessage());
        }
    }

    private void setErrorResponse(ServletResponse response, String errorMessage){
        ObjectMapper objectMapper = new ObjectMapper();
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpResponse.setCharacterEncoding("UTF-8");
        ErrorResponse errorResponse = new ErrorResponse(errorMessage, HttpStatus.UNAUTHORIZED.value());
        try {
            httpResponse.getWriter()
                .write(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Data
    public static class ErrorResponse{
        private final String message;
        private final Integer code;
    }
}
