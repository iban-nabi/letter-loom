package com.letter_loom.controllers;

import com.letter_loom.dtos.request_dto.RegisterUserRequest;
import com.letter_loom.dtos.request_dto.UpdateUserRequest;
import com.letter_loom.dtos.response_dto.UserResponse;
import com.letter_loom.entities.User;
import com.letter_loom.mappers.UserMapper;
import com.letter_loom.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register-user")
    public ResponseEntity<UserResponse> registerUser(
            UriComponentsBuilder uriComponentsBuilder,
            @RequestBody RegisterUserRequest request){

        User user = userMapper.toEntity(request);

        if(userRepository.findUserByEmail(user.getEmail())!=null){
            System.out.println("email already exist");
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);
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
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request){
        User user = userRepository.findById(id).orElse(null);
        if(user!=null){
            userMapper.updateEntity(request, user);
            userRepository.save(user);
            return ResponseEntity.ok(userMapper.toDto(user));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    public void deleteUser(){}
}
