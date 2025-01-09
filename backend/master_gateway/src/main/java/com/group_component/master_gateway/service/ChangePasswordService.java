package com.group_component.master_gateway.service;

import com.group_component.master_gateway.request.ChangePasswordRequest;
import com.group_component.master_gateway.response.MessageResponse;
import com.group_component.master_gateway.response.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ChangePasswordService {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsManager userDetailsManager;
    private final AuthenticatedUserService authenticatedUserService;

    public ChangePasswordService(PasswordEncoder passwordEncoder,
                                 UserDetailsManager userDetailsManager,
                                 AuthenticatedUserService authenticatedUserService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsManager = userDetailsManager;
        this.authenticatedUserService = authenticatedUserService;
    }

    public ResponseEntity<?> changePassword(ChangePasswordRequest changePasswordRequest) {
        Map<String, ArrayList<String>> errors = validateAttempt(changePasswordRequest);

        if (!errors.isEmpty()) {
            return ValidationErrorResponse.create(errors, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User.UserBuilder userBuilder = User.withUsername(this.authenticatedUserService.getAuthenticatedUser().getEmail())
                .password(this.passwordEncoder.encode(changePasswordRequest.getNewPassword()));

        this.userDetailsManager.updateUser(userBuilder.build());

        return MessageResponse.create("Password updated.", HttpStatus.OK);
    }

    public Map<String, ArrayList<String>> validateAttempt(ChangePasswordRequest changePasswordRequest) {
        Map<String, ArrayList<String>> errors = new HashMap<>();
        String actualPassword = this.authenticatedUserService.getAuthenticatedUser().getPassword();

        if (changePasswordRequest.getCurrentPassword() == null || changePasswordRequest.getCurrentPassword().isEmpty()) {
            errors.put("currentPassword", new ArrayList<>() {{
                add("Current password is required.");
            }});
        }

        if (changePasswordRequest.getNewPassword() == null || changePasswordRequest.getNewPassword().isEmpty()) {
            errors.put("newPassword", new ArrayList<>() {{
                add("New password is required.");
            }});
        }

        if (changePasswordRequest.getConfirm() == null || changePasswordRequest.getConfirm().isEmpty()) {
            errors.put("confirm", new ArrayList<>() {{
                add("Password confirmation is required.");
            }});
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        if (changePasswordRequest.getNewPassword().length() < 8) {
            errors.put("newPassword", new ArrayList<>() {{
                add("New password must be at least 8 characters long.");
            }});
        }

        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirm())) {
            errors.put("confirm", new ArrayList<>() {{
                add("Passwords do not match.");
            }});
        }

        if (!this.passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), actualPassword)) {
            errors.put("currentPassword", new ArrayList<>() {{
                add("Current password is incorrect.");
            }});
        }

        if (this.passwordEncoder.matches(changePasswordRequest.getNewPassword(), actualPassword)) {
            errors.put("newPassword", new ArrayList<>() {{
                add("Old and new passwords cannot be the same.");
            }});
        }

        return errors;
    }
}
