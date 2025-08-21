package com.letter_loom.filter;

import com.letter_loom.entities.Role;
import com.letter_loom.objects.Jwt;
import com.letter_loom.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Custom JWT Authentication Filter that intercepts incoming requests
 * and checks for a valid JWT in the Authorization header.
 * If valid, it sets the authentication in the SecurityContext.
 */
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Get the Authorization header from the request
        String authHeader = request.getHeader("Authorization");

        // If no header is found or it doesn't start with "Bearer ", skip JWT validation
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        // Extract the token by removing the "Bearer " prefix
        String token = authHeader.replace("Bearer ", "");

        // If the token is invalid, continue without setting authentication
        Jwt jwt = jwtService.parseToken(token);
        if(jwt==null || jwt.isExpired()){
            filterChain.doFilter(request,response);
            return;
        }

        String userId = jwt.getSubject();
        Role role = jwt.getRole();
        // Create an authentication object with the user ID as the principal
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        Long.parseLong(userId), // user's id (subject)
                        null, // no credentials needed
                        List.of(new SimpleGrantedAuthority("ROLE_"+role))
                );

        // Attach request-specific details (like IP, session, etc.) to the authentication
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Store the authentication in the SecurityContext for further use
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // Continue the filter chain
        filterChain.doFilter(request,response);
    }
}
