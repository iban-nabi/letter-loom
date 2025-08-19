package com.letter_loom.controllers;

import com.letter_loom.dtos.request_dto.LoginUserRequest;
import com.letter_loom.dtos.response_dto.JwtResponse;
import com.letter_loom.services.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@Valid @RequestBody LoginUserRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(request.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }

//    @PostMapping("/validate")
//    public boolean validate(@RequestHeader("Authorization") String authHeader){
//        authHeader = authHeader.replace("Bearer ", "");
//        return jwtService.validateToken(authHeader);
//    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(){
        return ResponseEntity.status(401).body(Map.of("login error", "Invalid Credentials"));
    }
}
