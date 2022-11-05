package com.nhnacademy.gw1;

import com.nhnacademy.gw1.currency.Currency;
import com.nhnacademy.gw1.currency.DollarCurrency;
import com.nhnacademy.gw1.currency.EuroCurrency;
import com.nhnacademy.gw1.currency.KoreanCurrency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BankTest {
    private Currency dollarCurrency;
    private Currency koreanCurrency;
    private Currency euroCurrency;

    private Bank bank;

    @BeforeEach
    @DisplayName("통화 종류 선언")
    void init(){
        dollarCurrency = new DollarCurrency();
        koreanCurrency = new KoreanCurrency();
        euroCurrency = new EuroCurrency();

        bank = new Bank();
    }

    @Test
    @DisplayName("1000원 환전 -> 1$")
    void bank_exchangeKoreanMoney_toDollarMoneyTest() {
        Money koreanMoney = new Money(new BigDecimal(1000), koreanCurrency);

        assertThat(bank.exchangeMoney(koreanMoney, dollarCurrency).getBalance().toString()).isEqualTo("1.00");
    }

    @Test
    @DisplayName("1000원 환전 -> 0.7€")
    void bank_exchangeKoreanMoney_toEuroMoneyTest() {
        Money koreanMoney = new Money(new BigDecimal(1000), koreanCurrency);

        assertThat(bank.exchangeMoney(koreanMoney, euroCurrency).getBalance().toString()).isEqualTo("0.70");
    }

    @Test
    @DisplayName("5.25$ 환전 -> 5250원")
    void bank_exchangeDollarMoney_toKoreanMoneyTest() {
        Money dollarMoney = new Money(new BigDecimal("5.25"), dollarCurrency);

        assertThat(bank.exchangeMoney(dollarMoney, koreanCurrency).getBalance().toString()).isEqualTo("5250");
    }

    @Test
    @DisplayName("1$ 환전 -> 0.70€")
    void bank_exchangeDollarMoney_toEuroMoneyTest() {
        Money dollarMoney = new Money(new BigDecimal("1"), dollarCurrency);

        assertThat(bank.exchangeMoney(dollarMoney, euroCurrency).getBalance().toString()).isEqualTo("0.70");
    }

    @Test
    @DisplayName("1.255$ -> 1260원 (반올림)")
    void bank_roundOffUp_when_exchangeDollarMoney_toKoreanMoney(){
        Money dollarMoney = new Money(new BigDecimal("1.255"), dollarCurrency);

        assertThat(bank.exchangeMoney(dollarMoney, koreanCurrency).getBalance().toString()).isEqualTo("1260");
    }

    @Test
    @DisplayName("1.255$ -> 0.88€ (반올림)")
    void bank_roundOffUp_when_exchangeDollarMoney_toEuroMoney(){
        Money dollarMoney = new Money(new BigDecimal("1.255"), dollarCurrency);

        assertThat(bank.exchangeMoney(dollarMoney, euroCurrency).getBalance().toString()).isEqualTo("0.88");
    }

    @Test
    @DisplayName("1.254$ -> 1250원 (반올림)_내림")
    void bank_roundOffDown_when_exchangeDollarMoney_toKoreanMoney(){
        Money dollarMoney = new Money(new BigDecimal("1.254"), dollarCurrency);

        assertThat(bank.exchangeMoney(dollarMoney, koreanCurrency).getBalance().toString()).isEqualTo("1250");
    }

    @Test
    @DisplayName("1.24$ -> 0.87€ (반올림)_내림")
    void bank_roundOffDown_when_exchangeDollarMoney_toEuroMoney(){
        Money dollarMoney = new Money(new BigDecimal("1.24"), dollarCurrency);

        assertThat(bank.exchangeMoney(dollarMoney, euroCurrency).getBalance().toString()).isEqualTo("0.87");
    }

    @Test
    @DisplayName("1255원 -> 1.26$ (반올림)")
    void bank_roundOffUp_when_exchangeKoreanMoney_toDollarMoney(){
        Money koreanMoney = new Money(new BigDecimal("1255"), koreanCurrency);

        assertThat(bank.exchangeMoney(koreanMoney, dollarCurrency).getBalance().toString()).isEqualTo("1.26");
    }

    @Test
    @DisplayName("1254원 -> 1.25 $ (반올림)")
    void bank_roundOffDown_when_exchangeKoreanMoney_toDollarMoney(){
        Money koreanMoney = new Money(new BigDecimal("1254"), koreanCurrency);

        assertThat(bank.exchangeMoney(koreanMoney, dollarCurrency).getBalance().toString()).isEqualTo("1.25");
    }
}
