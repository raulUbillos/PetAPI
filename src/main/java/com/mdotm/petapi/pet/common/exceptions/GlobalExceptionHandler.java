package com.mdotm.petapi.pet.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ErrorResponse> errorResponses = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setCode("BAD_ARGUMENT");
            errorResponse.setDescription(fieldError.getDefaultMessage());
            errorResponses.add(errorResponse);
        }
        return new ResponseEntity<>(errorResponses, HttpStatus.BAD_REQUEST);
    }
}