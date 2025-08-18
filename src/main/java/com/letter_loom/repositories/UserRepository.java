package com.letter_loom.repositories;

import com.letter_loom.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);
    User findByUsername(String username);
    boolean existsByEmail(String email);
}
