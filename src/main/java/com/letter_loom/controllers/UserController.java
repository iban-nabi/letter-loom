package com.letter_loom.controllers;

import com.letter_loom.entities.User;
import com.letter_loom.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    public void createUser(){}

    @GetMapping("user/{id}")
    public User getUserDetails(@PathVariable Long id){
        return userRepository.findById(id).orElse(null);
    }


    public void updateUser(){}
    public void deleteUser(){}
}
