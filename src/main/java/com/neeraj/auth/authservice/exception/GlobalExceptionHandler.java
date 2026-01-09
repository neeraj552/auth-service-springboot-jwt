package com.neeraj.auth.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.neeraj.auth.authservice.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequest(BadRequestException ex){
       return new ResponseEntity<>(
             new ApiResponse<>(false,ex.getMessage(),null),
             HttpStatus.BAD_REQUEST

       );

    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<?>> handleUnauthorized(UnauthorizedException ex){
        return new ResponseEntity<>(
            new ApiResponse<>(false,ex.getMessage(),null),
            HttpStatus.UNAUTHORIZED
        );
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(NotFoundException ex){
        return new ResponseEntity<>(
            new ApiResponse<>(false,ex.getMessage(),null),
            HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidation(MethodArgumentNotValidException ex){
        String msg = ex.getBindingResult().getFieldError().getDefaultMessage();
        return new ResponseEntity<>(
            new ApiResponse<>(false,msg,null),
            HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneric(Exception ex){
        return new ResponseEntity<>(
            new ApiResponse<>(false,"Something went wrong",null),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
