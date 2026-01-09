package com.neeraj.auth.authservice.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message){
        super(message);
    }


}
