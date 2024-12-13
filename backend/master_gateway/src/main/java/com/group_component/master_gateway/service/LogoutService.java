package com.group_component.master_gateway.service;

import com.group_component.master_gateway.dto.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LogoutService {
    public ResponseEntity<?> logout() {
        return MessageResponse.create("Logout.", HttpStatus.OK);
    }
}
