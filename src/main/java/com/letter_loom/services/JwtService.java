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


//    public boolean validateToken(String token) {
//        try {
//            Claims claims = getClaims(token); // extract claims from the token
//            return claims.getExpiration().after(new Date()); // check if expiration is still in the future
//        } catch (JwtException e) { // thrown if the token is invalid, tampered, or expired
//            return false; // token is not valid
//        }
//    }

    /**
     * Retrieves the subject from the JWT string
     * @param token the JWT  string to validate
     * @return the subject from the JWT
     */
//    public String getSubject(String token){
//        return getClaims(token).getSubject();
//    }

    /**
//     * Retrieves the role of a user from the JWT string
//     * @param token the JWT  string to validate
//     * @return the subject from the JWT
//     */
//    public Role getRole(String token){
//        return Role.valueOf(getClaims(token).get("role", String.class));
//    }


    /**
     * Parses and validates a JWT, then extracts its claims.
     * The token is verified using the configured secret key.
     *
     * @param token the JWT string
     * @return the Claims (payload) contained in the token
     * @throws io.jsonwebtoken.JwtException if the token is invalid or expired
     */
//    private Claims getClaims(String token) {
//        return Jwts.parser()
//                // verify the token's signature with the secret key
//                .verifyWith(jwtConfiguration.getSecretKey())
//                .build()
//                // parse the token and extract its claims (subject, expiration, custom fields, etc.)
//                .parseSignedClaims(token)
//                .getPayload(); // return the payload (Claims)
//    }
}
