package com.letter_loom.objects;

import com.letter_loom.entities.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.*;

import javax.crypto.SecretKey;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Jwt {
    private Claims claims;
    private SecretKey secretKey;

    /**
     * Validates a JWT token by checking its expiration.
     * If the token is invalid, malformed, or expired, it will return false.
     *
     * @return true if the token is valid and not expired, false otherwise
     */
    public boolean isExpired(){
        try {
            return claims.getExpiration().before(new Date()); // check if expiration is still in the future
        } catch (JwtException e) { // thrown if the token is invalid, tampered, or expired
            return true; // token is not valid
        }
    }

    public String getSubject(){
        return claims.getSubject();
    }

    public Role getRole(){
        return Role.valueOf(claims.get("role", String.class));
    }

    public String toString(){
        return Jwts.builder()
                .claims(claims)
                .signWith(secretKey)
                .compact();
    }

}
