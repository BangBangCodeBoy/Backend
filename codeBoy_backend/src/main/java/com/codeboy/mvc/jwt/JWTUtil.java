package com.codeboy.mvc.jwt;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private final SecretKey secretKey; // JWT 서명용 시크릿 키

    // application.yml / properties 에 정의된 값 주입
    // 예: spring.jwt.secret-key: my-jwt-secret-key...
    public JWTUtil(@Value("${spring.jwt.secret-key}") String secret) {
        this.secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }


    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("username", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

    public boolean isExpired(String token) {
        Date expiration = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();

        return expiration.before(new Date());
    }

    public String createJwt(String username, String role, Long expiredMs) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expiredMs))
                .signWith(secretKey)
                .compact();
    }
}
