package com.group5.engagement.exception;

import com.group5.engagement.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException ex) {

        ApiResponse<Void> res = ApiResponse.error(ex.getCode(), ex.getMessage());
        return ResponseEntity.badRequest().body(res);
    }
}
