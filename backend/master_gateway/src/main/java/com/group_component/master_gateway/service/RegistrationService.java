package com.group_component.master_gateway.service;

import com.group_component.master_gateway.dto.UserDTO;
import com.group_component.master_gateway.dto.response.MessageResponse;
import com.group_component.master_gateway.dto.response.ValidationErrorResponse;
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
public class RegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsManager userDetailsManager;

    public RegistrationService(PasswordEncoder passwordEncoder, UserDetailsManager userDetailsManager) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsManager = userDetailsManager;
    }

    public ResponseEntity<?> register(UserDTO userDTO) {
        Map<String, ArrayList<String>> errors = validateUser(userDTO);
        if (!errors.isEmpty()) {
            return ValidationErrorResponse.create(errors, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User.UserBuilder userBuilder = User.withUsername(userDTO.getEmail())
                .password(this.passwordEncoder.encode(userDTO.getPassword()))
                .roles("USER");

        this.userDetailsManager.createUser(userBuilder.build());

        return MessageResponse.create("Registered.", HttpStatus.CREATED);
    }

    public Map<String, ArrayList<String>> validateUser(UserDTO userDTO) {
        Map<String, ArrayList<String>> errors = new HashMap<>();

        if (this.userDetailsManager.userExists(userDTO.getEmail())) {
            errors.put("email", new ArrayList<>() {{
                add("Email is already registered.");
            }});
        }

        if (!userDTO.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.put("email", new ArrayList<>() {{
                add("Invalid email.");
            }});
        }

        if (userDTO.getPassword().length() < 8) {
            errors.put("password", new ArrayList<>() {{
                add("Password must be at least 8 characters long.");
            }});
        }

        if (!userDTO.getPassword().equals(userDTO.getConfirm())) {
            errors.put("confirm", new ArrayList<>() {{
                add("Passwords do not match.");
            }});
        }

        return errors;
    }
}
