package com.splitwise.exceptions;

public class InvalidExpenseException extends Throwable {
    public InvalidExpenseException(String s) {
        super(s);
    }
}
