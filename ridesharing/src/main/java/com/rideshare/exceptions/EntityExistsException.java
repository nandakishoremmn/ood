package com.rideshare.exceptions;

public class EntityExistsException extends RuntimeException {
    public EntityExistsException(String s) {
        super(s);
    }
}
