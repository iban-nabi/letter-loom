package com.letter_loom.services;

import com.letter_loom.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${spring.jwt.secret}")
    private String secret;

    public String generateToken(User user) {
        final long tokenExpiration = 86400; // token available for one day
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("username", user.getUsername())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token) {
        try{
            Claims claims = getClaims(token);
            return claims.getExpiration().after(new Date());
        }catch(JwtException e){
            return false;
        }
    }

    public String getSubject(String token){
        return getClaims(token).getSubject();
    }
}
