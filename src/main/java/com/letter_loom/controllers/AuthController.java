package com.letter_loom.controllers;

import com.letter_loom.config.JwtConfiguration;
import com.letter_loom.dtos.request_dto.LoginUserRequest;
import com.letter_loom.dtos.response_dto.JwtResponse;
import com.letter_loom.dtos.response_dto.UserResponse;
import com.letter_loom.entities.User;
import com.letter_loom.mappers.UserMapper;
import com.letter_loom.objects.Jwt;
import com.letter_loom.repositories.UserRepository;
import com.letter_loom.services.JwtService;
import com.letter_loom.utilities.AuthenticationContextHelper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtConfiguration jwtConfiguration;
    private final AuthenticationContextHelper authHelper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(
            @Valid @RequestBody LoginUserRequest request,
            HttpServletResponse response) {

        //authenticate the user through the authentication manager using the username and password
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        ); // BadCredentialsException will be thrown if error in logging in

        User user = userRepository.findByUsername(request.getUsername());
        String accessToken = jwtService.generateAccessToken(user).toString(); //create an access token
        String refreshToken = jwtService.generateRefreshToken(user).toString(); // create a refresh token

        // create a cookie for the generated refresh token for security purposes
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh-token");
        cookie.setMaxAge((int) jwtConfiguration.getRefreshTokenExpiration()); // 7 days
        cookie.setSecure(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponse(accessToken)); //return JWT Token
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshAccessToken(
            @CookieValue(value = "refreshToken") String refreshToken) {

        Jwt jwt = jwtService.parseToken(refreshToken);
        if(jwt==null || jwt.isExpired()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long id = Long.parseLong(jwt.getSubject());
        User user = userRepository.findById(id).orElseThrow();
        String accessToken = jwtService.generateRefreshToken(user).toString();

        return ResponseEntity.ok(new JwtResponse(accessToken));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(){
        return ResponseEntity.status(401).body(Map.of("login error", "Invalid Credentials"));
    }
}
