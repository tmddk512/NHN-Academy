package com.nhnacademy.gw1.currency;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class EuroCurrency implements Currency {
    private final String standardMoney = "0.7";

    private NumberFormat formatter = new DecimalFormat("0.00");

    @Override
    public boolean isSameCurrencyType(Currency currency) {
        if (currency instanceof EuroCurrency) {
            return true;
        }
        return false;
    }

    public String getStandardMoney() {
        return standardMoney;
    }

    public NumberFormat getFormatter() {
        return formatter;
    }
}
