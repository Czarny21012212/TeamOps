package com.example.Backend.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {
    private String secretKey = "";

    public String generateJWT(String email) throws NoSuchAlgorithmException {

        Map<String, Object> map = new HashMap<>();
        return Jwts.builder()
                .addClaims(map)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getKey())
                .compact();
    }

    public Key getKey() throws NoSuchAlgorithmException {
        byte[] keyBytes = MessageDigest.getInstance("SHA-256").digest();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        return "";
    }
}
