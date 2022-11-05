package com.nhnacademy.gw1;

import com.nhnacademy.gw1.currency.Currency;
import com.nhnacademy.gw1.exception.CanNotSubtractLargeMoneyFromSmallMoneyException;
import com.nhnacademy.gw1.exception.NegativeMoneyException;
import com.nhnacademy.gw1.exception.NotSameCurrencyException;

import java.math.BigDecimal;
import java.math.MathContext;

public class Money {
    private BigDecimal balance;
    private Currency currency;

    public Money(BigDecimal balance, Currency currency) {
        isNegativeMoney(balance);
        balance = roundOff(balance);

        this.balance = new BigDecimal(currency.getFormatter().format(balance));
        this.currency = currency;
    }

    public Money add(Money money) {
        isSameCurrencyType(money);

        return new Money(this.balance.add(money.getBalance()), currency);
    }

    public Money subtract(Money money) {
        isSameCurrencyType(money);
        isSmallMoneySubtractLargeMoney(money);

        return new Money(this.balance.subtract(money.getBalance()), currency);
    }

    public boolean equals(Money money) {
        isSameCurrencyType(money);

        if (this.getBalance().equals(money.getBalance())) {
            return true;
        }
        return false;
    }

    private BigDecimal roundOff(BigDecimal balance) {
        return balance.round(new MathContext(3));
    }

    private void isSmallMoneySubtractLargeMoney(Money money) {
        if (this.balance.compareTo(money.getBalance()) < 0) {
            throw new CanNotSubtractLargeMoneyFromSmallMoneyException(this, money);
        }
    }

    private void isSameCurrencyType(Money money) {
        if (!this.currency.isSameCurrencyType(money.getCurrency())) {
            throw new NotSameCurrencyException(this, money);
        }
    }

    private void isNegativeMoney(BigDecimal balance) {
        if (balance.compareTo(new BigDecimal(0)) < 0) {
            throw new NegativeMoneyException(balance);
        }
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Money{" +
                "balance=" + balance +
                ", currency=" + currency.getClass().getSimpleName() +
                '}';
    }
}
