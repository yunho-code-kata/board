package com.example.yunhoborad.common.exception;

import org.springframework.http.HttpStatus;

public class AuthorizationException extends HttpException {

    public AuthorizationException(String message, String input) {
        super(HttpStatus.UNAUTHORIZED, message, input);
    }
}
