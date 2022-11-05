package com.nhnacademy.gw1.exception;

public class SmsNotifyFailedException extends RuntimeException {
    public SmsNotifyFailedException(Long customerId) {
        super("Not found sms number: " + customerId);
    }

}
