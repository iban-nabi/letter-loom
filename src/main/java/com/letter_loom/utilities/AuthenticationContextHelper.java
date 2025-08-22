package com.letter_loom.utilities;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationContextHelper {
    public Long getUserIdFromAuthToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // retrieve the ID from the authentication token (ID is set as the subject)
        return(Long) authentication.getPrincipal();
    }
}
