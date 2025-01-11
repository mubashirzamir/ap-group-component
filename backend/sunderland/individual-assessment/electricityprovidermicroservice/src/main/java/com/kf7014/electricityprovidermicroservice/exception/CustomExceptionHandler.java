package com.kf7014.electricityprovidermicroservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * Global exception handler for the Electricity Provider Microservice.
 * <p>
 * This class handles various exceptions thrown by the application and returns appropriate HTTP responses.
 * </p>
 * @author Muhammad Naqvi
 * @version 1.0
 * @since 2024-11-27
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * Handles validation exceptions thrown when method arguments fail validation.
     *
     * <p>
     * Constructs a detailed error message listing all validation errors and returns an HTTP 400 Bad Request status.
     * </p>
     *
     * @param ex the {@link MethodArgumentNotValidException} containing validation error details
     * @return a {@link ResponseEntity} with the error message and HTTP status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder("Validation error(s): ");
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.append(error.getField())
                  .append(" ")
                  .append(error.getDefaultMessage())
                  .append("; ");
        });
        return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all generic exceptions not explicitly handled by other exception handlers.
     *
     * <p>
     * Logs the exception details (logging not shown here) and returns an HTTP 500 Internal Server Error status
     * with a generic error message.
     * </p>
     *
     * @param ex the {@link Exception} that was thrown
     * @return a {@link ResponseEntity} with the error message and HTTP status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        // Log the exception (implementation not shown)
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles resource not found exceptions.
     *
     * <p>
     * Returns an HTTP 404 Not Found status with the exception's message.
     * </p>
     *
     * @param ex the {@link ResourceNotFoundException} that was thrown
     * @return a {@link ResponseEntity} with the error message and HTTP status
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
