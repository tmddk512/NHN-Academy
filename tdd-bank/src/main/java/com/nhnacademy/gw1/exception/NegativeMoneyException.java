package com.nhnacademy.gw1.exception;

import java.math.BigDecimal;

public class NegativeMoneyException extends RuntimeException {
    public NegativeMoneyException(BigDecimal balance) {
        super(balance.toString() + " is Negative money.");
    }
}
