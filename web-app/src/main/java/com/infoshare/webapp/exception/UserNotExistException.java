package com.infoshare.webapp.exception;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserNotExistException  extends RuntimeException{
    public UserNotExistException(String message) {
        super(message);
    }
}
