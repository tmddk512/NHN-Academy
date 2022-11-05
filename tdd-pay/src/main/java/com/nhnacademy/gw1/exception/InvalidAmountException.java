package com.nhnacademy.gw1.exception;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(long amount) {
        super("Invalid amount: " + amount);
    }
}
