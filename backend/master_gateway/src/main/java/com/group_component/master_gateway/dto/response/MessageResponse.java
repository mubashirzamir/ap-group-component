package com.group_component.master_gateway.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static ResponseEntity<MessageResponse> create(String message, HttpStatus status) {
        return new ResponseEntity<>(new MessageResponse(message), status);
    }
}
