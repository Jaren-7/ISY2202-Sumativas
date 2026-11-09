package com.isy2202.duocplay.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service 
public class JwtService {
    
    private final SecretKey _secretKey;
    
    public JwtService(@Value("${duocplay.jwt.secret}") String secret) {

        byte[] keyBytes = Decoders.BASE64.decode(secret);

        _secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
        .subject(userDetails.getUsername())
        .issuedAt(new Date())
        .signWith(_secretKey, Jwts.SIG.HS256)
        .compact();
    }

    public String extractUsername(String token) {

        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            Claims claims = extractAllClaims(token);

            return claims.getSubject().equals(userDetails.getUsername()) && userDetails.isEnabled();
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
            .verifyWith(_secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
