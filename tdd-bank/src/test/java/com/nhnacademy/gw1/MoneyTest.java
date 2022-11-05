package com.nhnacademy.gw1;

import com.nhnacademy.gw1.currency.Currency;
import com.nhnacademy.gw1.currency.DollarCurrency;
import com.nhnacademy.gw1.currency.EuroCurrency;
import com.nhnacademy.gw1.currency.KoreanCurrency;
import com.nhnacademy.gw1.exception.CanNotSubtractLargeMoneyFromSmallMoneyException;
import com.nhnacademy.gw1.exception.NegativeMoneyException;
import com.nhnacademy.gw1.exception.NotSameCurrencyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class MoneyTest {
    private Currency dollarCurrency;
    private Currency koreanCurrency;
    private Currency euroCurrency;

    @BeforeEach
    @DisplayName("통화 종류 선언")
    void init(){
        dollarCurrency = new DollarCurrency();
        koreanCurrency = new KoreanCurrency();
        euroCurrency = new EuroCurrency();
    }

    @Test
    @DisplayName("달러 + 달러")
    void money_dollarCurrency_addTest() {
        Money moneyA = new Money(new BigDecimal(1000), dollarCurrency);
        Money moneyB = new Money(new BigDecimal(1000), dollarCurrency);

        Money sumMoney = moneyA.add(moneyB);

        assertThat(sumMoney.getBalance().longValue()).isEqualTo(2000);
    }

    @Test
    @DisplayName("원화 + 원화")
    void money_koreanCurrency_addTest() {
        Money moneyA = new Money(new BigDecimal(1000), koreanCurrency);
        Money moneyB = new Money(new BigDecimal(1000), koreanCurrency);

        Money sumMoney = moneyA.add(moneyB);

        assertThat(sumMoney.getBalance().longValue()).isEqualTo(2000);
    }

    @Test
    @DisplayName("유로 + 유로")
    void money_euroCurrency_addTest() {
        Money moneyA = new Money(new BigDecimal(2), euroCurrency);
        Money moneyB = new Money(new BigDecimal(2), euroCurrency);

        Money sumMoney = moneyA.add(moneyB);

        assertThat(sumMoney.getBalance().longValue()).isEqualTo(4);
    }

    @Test
    @DisplayName("달러 + 원화")
    void money_dollarCurrency_addKoreanCurrency_then_throwNotSameCurrencyException() {
        Money dollarMoney = new Money(new BigDecimal(1000), dollarCurrency);
        Money koreanMoney = new Money(new BigDecimal(1000), koreanCurrency);

        assertThatThrownBy(() -> dollarMoney.add(koreanMoney))
                .isInstanceOf(NotSameCurrencyException.class)
                .hasMessageContainingAll("Not Same Currency (DollarCurrency != KoreanCurrency)");
    }

    @Test
    @DisplayName("달러 + 유로")
    void money_dollarCurrency_addEuroCurrency_then_throwNotSameCurrencyException() {
        Money dollarMoney = new Money(new BigDecimal(1000), dollarCurrency);
        Money euroMoney = new Money(new BigDecimal(1), euroCurrency);

        assertThatThrownBy(() -> dollarMoney.add(euroMoney))
                .isInstanceOf(NotSameCurrencyException.class)
                .hasMessageContainingAll("Not Same Currency (DollarCurrency != EuroCurrency)");
    }

    @Test
    @DisplayName("유로 + 원화")
    void money_euroCurrency_addKoreanCurrency_then_throwNotSameCurrencyException() {
        Money euroMoney = new Money(new BigDecimal(1), euroCurrency);
        Money koreanMoney = new Money(new BigDecimal(1000), koreanCurrency);

        assertThatThrownBy(() -> euroMoney.add(koreanMoney))
                .isInstanceOf(NotSameCurrencyException.class)
                .hasMessageContainingAll("Not Same Currency (EuroCurrency != KoreanCurrency)");
    }

    @Test
    @DisplayName("Dollar_Money의 Balance의 값이 음수인 경우")
    void money_dollarBalanceIsNegative_then_throwNegativeMoneyException() {
        assertThatThrownBy(() -> new Money(new BigDecimal(-1), dollarCurrency))
                .isInstanceOf(NegativeMoneyException.class)
                .hasMessageContainingAll("-1 is Negative money.");
    }

    @Test
    @DisplayName("Korean_Money의 Balance의 값이 음수인 경우")
    void money_koreanBalanceIsNegative_then_throwNegativeMoneyException() {
        assertThatThrownBy(() -> new Money(new BigDecimal(-1000), koreanCurrency))
                .isInstanceOf(NegativeMoneyException.class)
                .hasMessageContainingAll("-1000 is Negative money.");
    }

    @Test
    @DisplayName("Euro_Money의 Balance의 값이 음수인 경우")
    void money_euroBalanceIsNegative_then_throwNegativeMoneyException() {
        assertThatThrownBy(() -> new Money(new BigDecimal(-1), euroCurrency))
                .isInstanceOf(NegativeMoneyException.class)
                .hasMessageContainingAll("-1 is Negative money.");
    }

    @Test
    @DisplayName("원화_2000원 == 원화_2000원")
    void money_koreanMoneys_samePriceComparisonTest() {
        Money moneyA = new Money(new BigDecimal(2000), new KoreanCurrency());
        Money moneyB = new Money(new BigDecimal(2000), new KoreanCurrency());

        assertThat(moneyA.equals(moneyB)).isTrue();
    }

    @Test
    @DisplayName("달러_2$ == 달러_2$")
    void money_dollarMoneys_samePriceComparisonTest() {
        Money moneyA = new Money(new BigDecimal(2), new DollarCurrency());
        Money moneyB = new Money(new BigDecimal(2), new DollarCurrency());

        assertThat(moneyA.equals(moneyB)).isTrue();
    }

    @Test
    @DisplayName("유로_2€ == 유로_2€")
    void money_euroMoneys_samePriceComparisonTest() {
        Money moneyA = new Money(new BigDecimal(2), new EuroCurrency());
        Money moneyB = new Money(new BigDecimal(2), new EuroCurrency());

        assertThat(moneyA.equals(moneyB)).isTrue();
    }

    @Test
    @DisplayName("원화_2000원 != 원화_3000원")
    void money_koreanMoneys_notSamePriceComparisonTest() {
        Money moneyA = new Money(new BigDecimal(2000), new KoreanCurrency());
        Money moneyB = new Money(new BigDecimal(3000), new KoreanCurrency());

        assertThat(moneyA.equals(moneyB)).isFalse();
    }

    @Test
    @DisplayName("달러_2$ != 달러_3$")
    void money_dollarMoneys_notSamePriceComparisonTest() {
        Money moneyA = new Money(new BigDecimal(2), new DollarCurrency());
        Money moneyB = new Money(new BigDecimal(3), new DollarCurrency());

        assertThat(moneyA.equals(moneyB)).isFalse();
    }

    @Test
    @DisplayName("유로_2€ != 유로_3€")
    void money_euroMoneys_notSamePriceComparisonTest() {
        Money moneyA = new Money(new BigDecimal(2), new EuroCurrency());
        Money moneyB = new Money(new BigDecimal(3), new EuroCurrency());

        assertThat(moneyA.equals(moneyB)).isFalse();
    }

    @Test
    @DisplayName("10$ - 5$ = 5$")
    void money_dollarCurrency_subtractTest() {
        Money moneyA = new Money(new BigDecimal(10), dollarCurrency);
        Money moneyB = new Money(new BigDecimal(5), dollarCurrency);

        Money sumMoney = moneyA.subtract(moneyB);

        assertThat(sumMoney.getBalance().toString()).isEqualTo("5.00");
    }

    @Test
    @DisplayName("1000원 - 500원 = 500원")
    void money_koreanCurrency_subtractTest() {
        Money moneyA = new Money(new BigDecimal(1000), koreanCurrency);
        Money moneyB = new Money(new BigDecimal(500), koreanCurrency);

        Money sumMoney = moneyA.subtract(moneyB);

        assertThat(sumMoney.getBalance().longValue()).isEqualTo(500);
    }

    @Test
    @DisplayName("10€ - 5€ = 5€")
    void money_euroCurrency_subtractTest() {
        Money moneyA = new Money(new BigDecimal(10), euroCurrency);
        Money moneyB = new Money(new BigDecimal(5), euroCurrency);

        Money sumMoney = moneyA.subtract(moneyB);

        assertThat(sumMoney.getBalance().toString()).isEqualTo("5.00");
    }

    @Test
    @DisplayName("5$ - 6$ = 오류")
    void money_smallDollar_subtractLargeDollar_then_throwCanNotSubtractLargeMoneyFromSmallMoneyException() {
        Money smallMoney = new Money(new BigDecimal(5), dollarCurrency);
        Money largeMoney = new Money(new BigDecimal(6), dollarCurrency);

        assertThatThrownBy(() -> smallMoney.subtract(largeMoney))
                .isInstanceOf(CanNotSubtractLargeMoneyFromSmallMoneyException.class)
                .hasMessageContainingAll(largeMoney + " can't subtract from " + smallMoney);
    }

    @Test
    @DisplayName("5000원 - 6000원 = 오류")
    void money_smallKorean_subtractLargeKorean_then_throwCanNotSubtractLargeMoneyFromSmallMoneyException() {
        Money smallMoney = new Money(new BigDecimal(5000), koreanCurrency);
        Money largeMoney = new Money(new BigDecimal(6000), koreanCurrency);

        assertThatThrownBy(() -> smallMoney.subtract(largeMoney))
                .isInstanceOf(CanNotSubtractLargeMoneyFromSmallMoneyException.class)
                .hasMessageContainingAll(largeMoney + " can't subtract from " + smallMoney);
    }

    @Test
    @DisplayName("5€ - 6€ = 오류")
    void money_smallEuro_subtractLargeEuro_then_throwCanNotSubtractLargeMoneyFromSmallMoneyException() {
        Money smallMoney = new Money(new BigDecimal(5), euroCurrency);
        Money largeMoney = new Money(new BigDecimal(6), euroCurrency);

        assertThatThrownBy(() -> smallMoney.subtract(largeMoney))
                .isInstanceOf(CanNotSubtractLargeMoneyFromSmallMoneyException.class)
                .hasMessageContainingAll(largeMoney + " can't subtract from " + smallMoney);
    }

    @Test
    @DisplayName("5.25$ + 5.25$ = 10.50$")
    void money_dollarCurrency_addDecimalTest() {
        Money moneyA = new Money(new BigDecimal("5.25"), dollarCurrency);
        Money moneyB = new Money(new BigDecimal("5.25"), dollarCurrency);

        Money sumMoney = moneyA.add(moneyB);

        assertThat(sumMoney.getBalance().toString()).isEqualTo("10.50");
    }
}
