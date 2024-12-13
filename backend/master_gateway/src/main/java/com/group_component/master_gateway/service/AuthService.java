package com.group_component.master_gateway.service;

import com.group_component.master_gateway.request.LoginRequest;
import com.group_component.master_gateway.request.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final RegistrationService registrationService;
    private final LoginService loginService;

    public AuthService(RegistrationService registrationService,
                       LoginService loginService) {
        this.registrationService = registrationService;
        this.loginService = loginService;
    }

    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        return this.registrationService.register(registerRequest);
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        return this.loginService.login(loginRequest);
    }
}
