package com.letter_loom.controllers;

import com.letter_loom.dtos.request_dto.RegisterUserRequest;
import com.letter_loom.dtos.request_dto.UpdateUserRequest;
import com.letter_loom.dtos.request_dto.UserPasswordRequest;
import com.letter_loom.dtos.response_dto.UserResponse;
import com.letter_loom.entities.User;
import com.letter_loom.mappers.UserMapper;
import com.letter_loom.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(
            UriComponentsBuilder uriComponentsBuilder,
            @Valid @RequestBody RegisterUserRequest request){

        User user = userMapper.toEntity(request);
        if(userRepository.existByEmail(user.getEmail())){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("email", "Email is already registered"));
        }else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

            URI uri = uriComponentsBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
            return ResponseEntity.created(uri).body(userMapper.toDto(user));
        }
    }

    public void loginUser(){

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserDetails(@PathVariable Long id){
        User user = userRepository.findById(id).orElse(null);
        if(user!=null){
            return ResponseEntity.ok(userMapper.toDto(user));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request){
        User user = userRepository.findById(id).orElse(null);
        if(user!=null){
            userMapper.updateEntity(request, user);
            userRepository.save(user);
            return ResponseEntity.ok(userMapper.toDto(user));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @Valid @RequestBody UserPasswordRequest userPasswordRequest){

        User user = userRepository.findById(id).orElse(null);

        if(user!=null){
            if(passwordEncoder.matches(userPasswordRequest.getOldPassword(), user.getPassword())){
                user.setPassword(passwordEncoder.encode(userPasswordRequest.getNewPassword()));
                userRepository.save(user);
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    public void deleteUser(){}
}
