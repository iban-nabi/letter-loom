package com.letter_loom.controllers;

import com.letter_loom.dtos.request_dto.LoginUserRequest;
import com.letter_loom.entities.User;
import com.letter_loom.mappers.UserMapper;
import com.letter_loom.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginUserRequest request){
        User user = userRepository.findByUsername(request.getUsername());
        if(user!=null){
            if(passwordEncoder.matches(request.getPassword(), user.getPassword())){
                return ResponseEntity.ok(userMapper.toDto(user));
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("password_error",
                        "Wrong Password", "status", 401));
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("username_error",
                    "User does not exist", "status", 404));
        }
    }
}
