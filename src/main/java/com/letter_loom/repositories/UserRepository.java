package com.letter_loom.repositories;

import com.letter_loom.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // create user

    // retrieve user
    User findUserById(Long id);

    // update user
    User updateUserById(Long id, User user);

    // delete user
}
