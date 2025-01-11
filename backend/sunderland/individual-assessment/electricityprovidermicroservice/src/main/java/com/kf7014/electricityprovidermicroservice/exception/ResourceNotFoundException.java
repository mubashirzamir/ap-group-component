package com.kf7014.electricityprovidermicroservice.exception;

/**
 * Exception thrown when a requested resource is not found.
 * <p>
 * This exception is typically used in service and controller layers to indicate that a specific resource
 * (e.g., meter reading) could not be located.
 * </p>
 * 
 * @see CustomExceptionHandler
 * @author Muhammad Naqvi
 * @version 1.0
 * @since 2024-11-27
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new {@code ResourceNotFoundException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
