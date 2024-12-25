package com.group_component.master_gateway.controller;

import com.group_component.master_gateway.request.LoginRequest;
import com.group_component.master_gateway.request.RegisterRequest;
import com.group_component.master_gateway.response.MessageResponse;
import com.group_component.master_gateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        return this.authService.register(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return this.authService.login(loginRequest);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return MessageResponse.create("Test.", HttpStatus.OK);
    }
}
