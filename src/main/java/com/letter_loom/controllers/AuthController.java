package com.letter_loom.controllers;

import com.letter_loom.config.JwtConfiguration;
import com.letter_loom.dtos.request_dto.LoginUserRequest;
import com.letter_loom.dtos.response_dto.JwtResponse;
import com.letter_loom.dtos.response_dto.UserResponse;
import com.letter_loom.entities.User;
import com.letter_loom.mappers.UserMapper;
import com.letter_loom.repositories.UserRepository;
import com.letter_loom.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
        String accessToken = jwtService.generateAccessToken(user); //create an access token
        String refreshToken = jwtService.generateRefreshToken(user); // create a refresh token

        // create a cookie for the generated refresh token for security purposes
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/auth");
        cookie.setMaxAge(jwtConfiguration.getRefreshTokenExpiration()); // 7 days
        cookie.setSecure(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponse(accessToken)); //return JWT Token
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(){
        //retrieve user's authentication token stored in SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // retrieve the ID from the authentication token (ID is set as the subject)
        Long id = (Long) authentication.getPrincipal();

        User user = userRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(){
        return ResponseEntity.status(401).body(Map.of("login error", "Invalid Credentials"));
    }
}
