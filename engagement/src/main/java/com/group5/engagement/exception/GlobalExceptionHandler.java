package com.group5.engagement.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> handleBaseException(BaseException ex) {

        Map<String, Object> response = new HashMap<>();

        response.put("errorCode", ex.getCode());
        response.put("message", ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }
}
