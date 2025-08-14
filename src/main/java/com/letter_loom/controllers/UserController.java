package com.letter_loom.controllers;

import com.letter_loom.dtos.UserDto;
import com.letter_loom.entities.User;
import com.letter_loom.mappers.UserMapper;
import com.letter_loom.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(String password){
        passwordEncoder.encode(password);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable Long id){
        User user = userRepository.findById(id).orElse(null);
        if(user!=null){
            return ResponseEntity.ok(userMapper.toDto(user));
        }else{
            return ResponseEntity.notFound().build();
        }
    }


    public void updateUser(){}
    public void deleteUser(){}
}
