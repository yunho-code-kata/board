package com.example.yunhoborad.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends HttpException {

    public InvalidTokenException(String message, String input) {
        super(HttpStatus.UNAUTHORIZED, message, input);
    }
}
