package com.nhnacademy.gw1.exception;

import com.nhnacademy.gw1.Money;

public class CanNotSubtractLargeMoneyFromSmallMoneyException extends RuntimeException {
    public CanNotSubtractLargeMoneyFromSmallMoneyException(Money smallMoney, Money largeMoney) {
        super(largeMoney + " can't subtract from " + smallMoney);
    }
}
