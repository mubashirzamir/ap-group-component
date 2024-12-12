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
public class UserDetailsService {
    public UserDetailsService() {
    }

    public ResponseEntity<?> register(UserDTO userDTO,
                                      UserDetailsManager userDetailsManager,
                                      PasswordEncoder passwordEncoder) {
        if (userDetailsManager.userExists(userDTO.email())) {
            return MessageResponse.create("User already exists.", HttpStatus.CONFLICT);
        }

        Map<String, ArrayList<String>> errors = validateUser(userDTO);
        if (!errors.isEmpty()) {
            return ValidationErrorResponse.create(errors, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User.UserBuilder userBuilder = User.withUsername(userDTO.email())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .roles("USER");

        userDetailsManager.createUser(userBuilder.build());

        return MessageResponse.create("Registered.", HttpStatus.CREATED);
    }

    public Map<String, ArrayList<String>> validateUser(UserDTO userDTO) {
        Map<String, ArrayList<String>> errors = new HashMap<>();

        if (!userDTO.email().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.put("email", new ArrayList<>() {{
                add("Invalid email.");
            }});
        }

        if (userDTO.getPassword().length() < 8) {
            errors.put("password", new ArrayList<>() {{
                add("Password must be at least 8 characters long.");
            }});
        }

        return errors;
    }
}
