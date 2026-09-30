package com.example.buildingmanagement.exception;

import com.example.buildingmanagement.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicatedArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleDuplicatedArgumentException(DuplicatedArgumentException ex) {
        return new ApiResponse<>(
                false,
                ex.getMessage(),
                null,
                null,
                400
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ApiResponse<>(
                false,
                ex.getMessage(),
                null,
                null,
                404
        );
    }

    @ExceptionHandler(FinishedBuildingStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleFinishedBuildingStatusException(FinishedBuildingStatusException ex) {
        return new ApiResponse<>(
                false,
                ex.getMessage(),
                null,
                null,
                400
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                (error) ->  errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ApiResponse<>(
                false,
                "Method Argument Not Valid Exception",
                null,
                errors,
                400
        );
    }
}
