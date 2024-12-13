package com.group_component.master_gateway.controller;

import com.group_component.master_gateway.dto.UserDTO;
import com.group_component.master_gateway.dto.response.MessageResponse;
import com.group_component.master_gateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        return this.authService.register(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        return this.authService.login(userDTO);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        return this.authService.logout();
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return MessageResponse.create("Test.", HttpStatus.OK);
    }
}
