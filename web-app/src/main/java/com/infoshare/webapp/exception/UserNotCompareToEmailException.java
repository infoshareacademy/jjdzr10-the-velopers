package com.infoshare.webapp.exception;

public class UserNotCompareToEmailException extends RuntimeException{
    public UserNotCompareToEmailException(String message) {
        super(message);
    }
}
