package com.group_component.master_gateway.service;

import com.group_component.master_gateway.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class DeleteService {
    private final UserDetailsManager userDetailsManager;
    private final AuthenticatedUserService authenticatedUserService;

    public DeleteService(UserDetailsManager userDetailsManager, AuthenticatedUserService authenticatedUserService) {
        this.userDetailsManager = userDetailsManager;
        this.authenticatedUserService = authenticatedUserService;
    }

    public ResponseEntity<?> delete(String email) {
        if (validateAttempt(email) != null) {
            return validateAttempt(email);
        }

        this.userDetailsManager.deleteUser(email);

        return MessageResponse.create("Deleted.", HttpStatus.OK);
    }

    private ResponseEntity<?> validateAttempt(String email) {
        if (!this.authenticatedUserService.getAuthenticatedUser().getEmail().equals(email)) {
            return MessageResponse.create("Deleted.", HttpStatus.OK);
        }

        return null;
    }
}
