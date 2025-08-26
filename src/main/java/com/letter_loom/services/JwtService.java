package com.letter_loom.services;

import com.letter_loom.config.JwtConfiguration;
import com.letter_loom.entities.User;
import com.letter_loom.objects.Jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtConfiguration jwtConfiguration;

    /**
     * Generates an Access Token for the given user.
     * The token includes the user's ID (as subject), basic profile information
     * (username, firstName, lastName) as claims, the issue time, and an expiration.
     * It is then signed using the configured secret key (HMAC).
     *
     * @param user the User entity containing user information
     * @return a signed JWT string
     */
    public Jwt generateAccessToken(User user) {
        return generateJwt(user, jwtConfiguration.getAccessTokenExpiration());
    }

    /**
     * Generates a Refresh Token for the given user.
     * The token includes the user's ID (as subject), basic profile information
     * (username, firstName, lastName) as claims, the issue time, and an expiration.
     * It is then signed using the configured secret key (HMAC).
     *
     * @param user the User entity containing user information
     * @return a signed JWT string
     */
    public Jwt generateRefreshToken(User user) {
        return generateJwt(user, jwtConfiguration.getRefreshTokenExpiration());
    }

    private Jwt generateJwt(User user, long expiration) {
        Claims claims = Jwts.claims()
                .subject(user.getId().toString()) // set user ID as subject
                .add("username", user.getUsername()) // include username claim
                .add("firstName", user.getFirstName()) // include first name claim
                .add("lastName", user.getLastName()) // include last name claim
                .add("role", user.getRole()) // include the role of the user
                .issuedAt(new Date()) // issue time
                .expiration(new Date(System.currentTimeMillis() + 1000 * expiration)) // expiry time
                .build(); // build and return token string

        return new Jwt(claims,jwtConfiguration.getSecretKey());
    }

    public Jwt parseToken(String token) {
        try{
            Claims claims = Jwts.parser()
                    // verify the token's signature with the secret key
                    .verifyWith(jwtConfiguration.getSecretKey())
                    .build()
                    // parse the token and extract its claims (subject, expiration, custom fields, etc.)
                    .parseSignedClaims(token)
                    .getPayload(); // return the payload (Claims)
            return new Jwt(claims,jwtConfiguration.getSecretKey());
        }catch(JwtException e){
            return null;
        }
    }
}
