
package com.klu.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET =
            "MySuperSecretKeyForJWTValidation123456";

    private static SecretKey getKey() {
        return Keys.hmacShaKeyFor(
                SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // Generate a test JWT token
    public static String generateTestToken() {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .subject("Hemangini")
                .claim("roles", "USER")
                .issuedAt(new Date(now))
                .expiration(new Date(now + 600000))
                .signWith(getKey())
                .compact();
    }

    // Validate JWT and extract claims
    public static Claims validateToken(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
