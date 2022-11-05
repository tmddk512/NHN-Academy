package com.nhnacademy.gw1.exception;

import com.nhnacademy.gw1.Money;

public class NotSameCurrencyException extends RuntimeException {
    public NotSameCurrencyException(Money moneyA, Money moneyB) {
        super("Not Same Currency (" + moneyA.getCurrency().getClass().getSimpleName() + " != " + moneyB.getCurrency().getClass().getSimpleName() + ")");
    }
}
