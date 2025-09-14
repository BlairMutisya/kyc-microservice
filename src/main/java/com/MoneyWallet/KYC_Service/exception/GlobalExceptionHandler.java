package com.MoneyWallet.KYC_Service.exception;

import com.MoneyWallet.KYC_Service.common.BaseApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * A centralized handler for all exceptions thrown by @RestController controllers.
 * This ensures a consistent error response format across the entire API.
 */
@RestControllerAdvice
@Slf4j // For logging
public class GlobalExceptionHandler {

    /**
     * Handles when a resource is not found (e.g., KYC record not found for user).
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseApiResponse<?>> handleResourceNotFound(ResourceNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        BaseApiResponse<?> response = BaseApiResponse.error(
                "NOT_FOUND",
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles violations of business rules (e.g., modifying a submitted KYC).
     */
    @ExceptionHandler(BusinessRuleViolationException.class)
    public ResponseEntity<BaseApiResponse<?>> handleBusinessRuleViolation(BusinessRuleViolationException ex) {
        log.warn("Business rule violated: {}", ex.getMessage());
        BaseApiResponse<?> response = BaseApiResponse.error(
                "CONFLICT",
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * Handles validation errors from @Valid annotations on DTOs.
     * This provides a detailed list of all field errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseApiResponse<?>> handleValidationErrors(MethodArgumentNotValidException ex) {
        log.warn("Validation error: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        BaseApiResponse<?> response = BaseApiResponse.error(
                "BAD_REQUEST",
                "Validation failed for one or more fields",
                errors // This will include the map of field-specific errors
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles file uploads that are too large.
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<BaseApiResponse<?>> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        log.warn("File upload size exceeded: {}", ex.getMessage());
        BaseApiResponse<?> response = BaseApiResponse.error(
                "PAYLOAD_TOO_LARGE",
                "The uploaded file is too large. Please check the size limit.",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    /**
     * Fallback handler for any other uncaught exception.
     * This prevents sensitive exception details from being leaked to the client.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseApiResponse<?>> handleAllUncaughtException(Exception ex) {
        log.error("An unexpected error occurred: ", ex); // Detailed log for debugging

        BaseApiResponse<?> response = BaseApiResponse.error(
                "INTERNAL_SERVER_ERROR",
                "An unexpected internal error occurred. Please try again later.",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}