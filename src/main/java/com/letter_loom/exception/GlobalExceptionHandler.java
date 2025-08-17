package com.letter_loom.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * This class modifies the return content of the  MethodArgumentNotValidException whenever an
     * exception is thrown by a method/function under the controller class due to an invalid DTO
     * request that is sent by the client (e.g. empty string, invalid character/s, too short or too
     * long string, invalid email format, invalid password, etc.)
     * @param exception MethodArgumentNotValidException
     * @return returns a Response Entity Bad Request with a body indicating the error and message stored
     *         in a key-value pair format.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException exception){

        Map<String, String> errors = new HashMap<>();

        // Get each field errors and store it and their default message in a Hash Map
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }
}
