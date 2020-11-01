package com.learn.exceptions;

public class UserRegistrationException extends RuntimeException {
    public UserRegistrationException(String msg) {
        super(msg);
    }

}
