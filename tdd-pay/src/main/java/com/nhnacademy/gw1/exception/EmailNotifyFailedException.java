package com.nhnacademy.gw1.exception;

public class EmailNotifyFailedException extends RuntimeException {
    public EmailNotifyFailedException(Long customerId) {
        super("Not found email address: " + customerId);
    }
}