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

    /**
     * Generates a JWT for the given user.
     * The token includes the user's ID (as subject), basic profile information
     * (username, firstName, lastName) as claims, the issue time, and an expiration.
     * It is then signed using the configured secret key (HMAC).
     *
     * @param user the User entity containing user information
     * @return a signed JWT string
     */
    public String generateToken(User user) {
        final long tokenExpirationSeconds = 86400; // 24 hours
        return Jwts.builder()
                .subject(user.getId().toString()) // set user ID as subject
                .claim("username", user.getUsername()) // include username claim
                .claim("firstName", user.getFirstName()) // include first name claim
                .claim("lastName", user.getLastName()) // include last name claim
                .issuedAt(new Date()) // issue time
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpirationSeconds)) // expiry time
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())) // sign using HMAC with secret key
                .compact(); // build and return token string
    }

    /**
     * Parses and validates a JWT, then extracts its claims.
     * The token is verified using the configured secret key.
     *
     * @param token the JWT string
     * @return the Claims (payload) contained in the token
     * @throws io.jsonwebtoken.JwtException if the token is invalid or expired
     */
    public Claims getClaims(String token) {
        return Jwts.parser()
                // verify the token's signature with the secret key
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                // parse the token and extract its claims (subject, expiration, custom fields, etc.)
                .parseSignedClaims(token)
                .getPayload(); // return the payload (Claims)
    }


    /**
     * Validates a JWT token by checking its expiration.
     * If the token is invalid, malformed, or expired, it will return false.
     *
     * @param token the JWT string to validate
     * @return true if the token is valid and not expired, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Claims claims = getClaims(token); // extract claims from the token
            return claims.getExpiration().after(new Date()); // check if expiration is still in the future
        } catch (JwtException e) { // thrown if the token is invalid, tampered, or expired
            return false; // token is not valid
        }
    }

    /**
     * Retrieves the subject from the JWT string
     * @param token the JWT  string to validate
     * @return the subject from the JWT
     */
    public String getSubject(String token){
        return getClaims(token).getSubject();
    }
}
