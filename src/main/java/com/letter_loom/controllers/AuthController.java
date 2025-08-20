package com.letter_loom.controllers;

import com.letter_loom.dtos.request_dto.LoginUserRequest;
import com.letter_loom.dtos.response_dto.JwtResponse;
import com.letter_loom.dtos.response_dto.UserResponse;
import com.letter_loom.entities.User;
import com.letter_loom.mappers.UserMapper;
import com.letter_loom.repositories.UserRepository;
import com.letter_loom.services.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
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

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@Valid @RequestBody LoginUserRequest request){
        //authenticate the user through the authentication manager using the username and password
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        ); // BadCredentialsException will be thrown if error in logging in

        User user = userRepository.findByUsername(request.getUsername());
        String token = jwtService.generateToken(user); //create a token
        return ResponseEntity.ok(new JwtResponse(token)); //return JWT Token
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
