package com.example.yunhoborad.common.auth;

import com.example.yunhoborad.common.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtTokenProvider {

    private static final String SCHEME = "Bearer ";

    private final SecretKey secretKey;
    private final Long expiredTime;
    private final TokenTheftDetector tokenTheftDetector;

    public JwtTokenProvider(
            @Value("${app.auth.token.secret-key") String secretKey,
            @Value("${app.auth.token.expired-time") Long expiredTime,
            TokenTheftDetector tokenTheftDetector
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.expiredTime = expiredTime;
        this.tokenTheftDetector = tokenTheftDetector;
    }

    public String createAccessToken(Long id) {
        Instant issuedAt = Instant.now();
        return SCHEME + Jwts.builder()
                .setExpiration(Date.from(issuedAt.plus(expiredTime, ChronoUnit.MINUTES)))
                .setIssuedAt(Date.from(issuedAt))
                .setSubject(String.valueOf(id))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(Long id) {
        String refreshToken = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(String.valueOf(id))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        tokenTheftDetector.update(id, refreshToken);
        return SCHEME + refreshToken;
    }

    public void detectTokenTheft(String tokenWithScheme) {
        if (tokenWithScheme == null) {
            throw new InvalidTokenException("토큰을 전송해주세요. 입력된 토큰: ", null);
        }

        Long memberId = parseToken(tokenWithScheme);
        String token = tokenWithScheme.substring(SCHEME.length());

        if (tokenTheftDetector.isDetected(memberId, token)) {
            throw new InvalidTokenException("토큰이 탈취되었습니다. 입력된 토큰: ", tokenWithScheme);
        }
    }

    public Long parseToken(String token) {
        if (!token.startsWith(SCHEME)) {
            throw new InvalidTokenException("유효하지 않은 토큰 타입입니다. 입력된 token: ", token);
        }
        String credentials = token.substring(SCHEME.length());
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();
        Claims claims = parser.parseClaimsJws(credentials)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }
}
