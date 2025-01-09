package com.group_component.master_gateway.service;

import com.group_component.master_gateway.model.AuthenticatedUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserService {
    public AuthenticatedUser getAuthenticatedUser() throws IllegalStateException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return new AuthenticatedUser(userDetails.getUsername(), userDetails.getPassword());
        }

        throw new IllegalStateException("User is not authenticated");
    }
}
