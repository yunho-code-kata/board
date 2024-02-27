package com.example.yunhoborad.common.auth;

import com.example.yunhoborad.common.exception.InvalidTokenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthContext authContext;

    public AuthInterceptor(JwtTokenProvider jwtTokenProvider, AuthContext authContext) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authContext = authContext;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isExcludedMethodAndUri(request)) {
            return true;
        }

        if (authHeader == null) {
            throw new InvalidTokenException("인증 정보가 없습니다. 입력된 token: ", null);
        }
        Long memberId = jwtTokenProvider.parseToken(authHeader);
        authContext.setAuth(memberId);
        return true;
    }

    private boolean isExcludedMethodAndUri(HttpServletRequest request) {
        if (request.getMethod().equals("GET") && request.getRequestURI().equals("/boards")) {
            return true;
        }
        return false;
    }
}
