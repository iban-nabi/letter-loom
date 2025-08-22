package com.letter_loom.services;

import com.letter_loom.entities.User;
import com.letter_loom.repositories.UserRepository;
import com.letter_loom.utilities.AuthenticationContextHelper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AuthenticationContextHelper authHelper;

    /**
     * Loads a user by their username from the database.
     * This method is used by Spring Security's AuthenticationManager/Provider
     * during the authentication process.
     *
     * @param username the username provided during login
     * @return a UserDetails object (Spring Security's User) containing the
     *         username, password, and authorities (currently an empty list)
     * @throws UsernameNotFoundException if no user is found with the given username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // returns a user object with the username, password, and list of granted authorities (temporary empty)
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),
                Collections.emptyList());
    }

    public User getUser(){
        Long id = authHelper.getUserIdFromAuthToken();
        return userRepository.findById(id).orElseThrow();
    }
}
