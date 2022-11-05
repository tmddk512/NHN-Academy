package com.nhnacademy.gw1.currency;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class KoreanCurrency implements Currency{
    private final String standardMoney = "1000";

    private NumberFormat formatter = new DecimalFormat("#");

    @Override
    public boolean isSameCurrencyType(Currency currency) {
        if (currency instanceof KoreanCurrency) {
            return true;
        }
        return false;
    }

    public String getStandardMoney() {
        return standardMoney;
    }

    @Override
    public NumberFormat getFormatter() {
        return formatter;
    }
}
