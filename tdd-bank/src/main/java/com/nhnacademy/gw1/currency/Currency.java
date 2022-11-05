package com.nhnacademy.gw1.currency;

import java.text.NumberFormat;

public interface Currency {

    // 같은 통화단위인지 확인
    boolean isSameCurrencyType(Currency currency);

    // 1000원 기준으로 환전된 돈
    String getStandardMoney();

    // 나라별 돈 형식에 맞게 포맷
    NumberFormat getFormatter();
}
