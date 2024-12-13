package com.group_component.master_gateway.service;

import com.group_component.master_gateway.dto.UserDTO;
import com.group_component.master_gateway.dto.response.ValidationErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public LoginService(UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<?> login(UserDTO userDTO) {
        Map<String, ArrayList<String>> errors = validateAttempt(userDTO);
        if (!errors.isEmpty()) {
            return ValidationErrorResponse.create(errors);
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userDTO.getEmail(),
                userDTO.getPassword()
        );

        Authentication authentication = this.authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(userDTO.toLoginResponse());
    }

    public Map<String, ArrayList<String>> validateAttempt(UserDTO userDTO) {
        Map<String, ArrayList<String>> errors = new HashMap<>();

        if (this.userDetailsService.loadUserByUsername(userDTO.getEmail()) == null) {
            errors.put("email", new ArrayList<>() {{
                add("Email is not registered.");
            }});
        }

        if (ObjectUtils.isEmpty(userDTO.getEmail())) {
            errors.put("email", new ArrayList<>() {{
                add("Email is required.");
            }});
        }

        if (ObjectUtils.isEmpty(userDTO.getPassword())) {
            errors.put("password", new ArrayList<>() {{
                add("Password is required.");
            }});
        }

        return errors;
    }
}
