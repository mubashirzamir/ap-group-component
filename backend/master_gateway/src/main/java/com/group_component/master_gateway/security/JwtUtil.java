package com.group_component.master_gateway.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "mv8XQIqDvsqm8T2E2I9vC8cHhCi4pmXE";
    private final long EXPIRATION_TIME = 86400000; // 24 hours

    private SecretKey secretKey;
    private final JwtParser jwtParser;
    private final JwtBuilder jwtBuilder;

    public JwtUtil() {
        this.secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
        this.jwtBuilder = Jwts.builder().signWith(secretKey);
    }

    public String generateToken(String username) {
        return this.jwtBuilder
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
    }

    public String extractUsername(String token) {
        return this.jwtParser
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return this.jwtParser
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }
}