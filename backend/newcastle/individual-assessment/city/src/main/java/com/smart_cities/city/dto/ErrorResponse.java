package com.smart_cities.city.dto;

public class ErrorResponse {
    private final String error;

    private ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public static ErrorResponse create(String error) {
        return new ErrorResponse(error);
    }
}