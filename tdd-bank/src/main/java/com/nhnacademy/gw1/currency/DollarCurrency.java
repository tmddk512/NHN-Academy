package com.nhnacademy.gw1.currency;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DollarCurrency implements Currency {
    private final String standardMoney = "1";

    private NumberFormat formatter = new DecimalFormat("0.00");

    @Override
    public boolean isSameCurrencyType(Currency currency) {
        if (currency instanceof DollarCurrency) {
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
