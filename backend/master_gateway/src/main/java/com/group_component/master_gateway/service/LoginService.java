package com.group_component.master_gateway.service;

import com.group_component.master_gateway.request.LoginRequest;
import com.group_component.master_gateway.response.LoginResponse;
import com.group_component.master_gateway.response.ValidationErrorResponse;
import com.group_component.master_gateway.response.dto.SanitizedUser;
import com.group_component.master_gateway.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginService(UserDetailsService userDetailsService, AuthenticationManager authenticationManager,
                        JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Map<String, ArrayList<String>> errors = validateAttempt(loginRequest);
        if (!errors.isEmpty()) {
            return ValidationErrorResponse.create(errors);
        }

        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            return LoginResponse.create(this.jwtUtil.generateToken(loginRequest.getEmail()),
                    SanitizedUser.instance(this.userDetailsService.loadUserByUsername(loginRequest.getEmail())));
        } catch (Exception e) {
            return ValidationErrorResponse.create(new HashMap<>() {{
                put("password", new ArrayList<>() {{
                    add("Invalid password.");
                }});
            }}, HttpStatus.UNAUTHORIZED);
        }
    }

    public Map<String, ArrayList<String>> validateAttempt(LoginRequest loginRequest) {
        Map<String, ArrayList<String>> errors = new HashMap<>();

        try {
            this.userDetailsService.loadUserByUsername(loginRequest.getEmail());
        } catch (UsernameNotFoundException e) {
            errors.put("email", new ArrayList<>() {{
                add("Email is not registered.");
            }});

            return errors;
        }

        if (ObjectUtils.isEmpty(loginRequest.getEmail())) {
            errors.put("email", new ArrayList<>() {{
                add("Email is required.");
            }});
        }

        if (ObjectUtils.isEmpty(loginRequest.getPassword())) {
            errors.put("password", new ArrayList<>() {{
                add("Password is required.");
            }});
        }

        return errors;
    }
}
