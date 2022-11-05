package com.nhnacademy.gw1.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long customerId) {
        super("Not found customer: " + customerId);
    }
}
