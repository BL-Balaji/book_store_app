package com.bookstore.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT Utility class for token generation and validation
 * Compatible with jjwt 0.11.5
 *
 * @author BL Balaji
 */
public class JwtUtil {

    private static final String SECRET_KEY = "bookstore-secret-key-for-jwt-token-generation-minimum-256-bits";
    private static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60 * 1000L; // 24 hours

    private static Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public static String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, username);
    }

    private static String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    public static String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }
}
