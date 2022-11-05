package com.nhnacademy.gw1;

import com.nhnacademy.gw1.currency.Currency;

import java.math.BigDecimal;

public class Bank {

    public Money exchangeMoney(Money unexchagedMoney, Currency exchangeCurrency) {
        BigDecimal dividedStandard = unexchagedMoney.getBalance().divide(new BigDecimal(unexchagedMoney.getCurrency().getStandardMoney()));

        return new Money(dividedStandard.multiply(new BigDecimal(exchangeCurrency.getStandardMoney())), exchangeCurrency);
    }
}
