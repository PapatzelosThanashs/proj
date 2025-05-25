package com.project.demo.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.web.server.ResponseStatusException;
import com.project.demo.dto.ErrorResponse;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;
import org.springframework.validation.FieldError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;



@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /* Handle database errors */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccess(DataAccessException ex, WebRequest request) {
        log.error("Database error: {}", ex.getMessage(), ex);
        return buildErrorResponse("Database error occurred", HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /* Catch-all for unexpected exceptions */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllOtherExceptions(Exception ex, WebRequest request) {
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        return buildErrorResponse("Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


    /* Custom application exception , not used, just for demonstration*/
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex, WebRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }


    /*  Handle @Valid validation errors (e.g., @NotNull, @Pattern) */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {

        String errors="";

      for (FieldError error : ex.getBindingResult().getFieldErrors()) {
        errors= errors + "---"+ error.getDefaultMessage()  ;
    }

       return buildErrorResponse("Validation error: " + errors, HttpStatus.BAD_REQUEST, request);
    }

    /* Handle Optimistic Locking Failure Exception */
     @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponse> handleOptimisticLocking(OptimisticLockingFailureException ex, WebRequest request) {
        log.error("Optimistic Locking error: {}", ex.getMessage(), ex);
        return buildErrorResponse("Update conflict: " + ex.getMessage(), HttpStatus.CONFLICT, request);
    }

    /* Handle illegal arguments (e.g., invalid enum value conversion) */
   @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }


    /*   Handle Spring's ResponseStatusException , getReason() available*/
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        log.warn("Handled ResponseStatusException: {}", ex.getReason(), ex);
        return buildErrorResponse(ex.getReason(), HttpStatus.valueOf(ex.getStatusCode().value()), request);
    }

    /*Jackson deserialization error, add it for birthdate LocalDate type. When the client tries to input different format. */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormat(HttpMessageNotReadableException ex, WebRequest request) {
        String message = "Invalid input format: " + ex.getMostSpecificCause().getMessage();
        return buildErrorResponse(message, HttpStatus.BAD_REQUEST, request);
    }

    /* Response based on ErrorResponseDto*/
    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus status, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getDescription(false).replace("uri=", ""),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, status);
        // using static methods
        // return ResponseEntity.status(status).body(error);
    }

 

}
