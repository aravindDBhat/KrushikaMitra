package com.krushikaMitra.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JWTService {

    private static final long EXPIRATION_TIME = 86400000; // 1 day in ms

    private final Key signingKey;

    public JWTService(@Value("${jwt.secret}") String secret) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // ✅ Generate JWT token
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Extract email (subject) from token
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // ✅ Validate token against UserDetails
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // ✅ Check expiration
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // ✅ Helper to parse claims
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
