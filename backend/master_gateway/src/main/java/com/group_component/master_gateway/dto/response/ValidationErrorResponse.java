package com.group_component.master_gateway.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Map;

public class ValidationErrorResponse {
    private Map<String, ArrayList<String>> errors;

    public ValidationErrorResponse(Map<String, ArrayList<String>> errors) {
        this.errors = errors;
    }

    public Map<String, ArrayList<String>> getErrors() {
        return errors;
    }

    public static ResponseEntity<ValidationErrorResponse> create(Map<String, ArrayList<String>> errors, HttpStatus status) {
        return new ResponseEntity<>(new ValidationErrorResponse(errors), status);
    }
}
