package com.nhnacademy.gw1;

import com.nhnacademy.gw1.exception.CustomerNotFoundException;
import com.nhnacademy.gw1.exception.EmailNotifyFailedException;
import com.nhnacademy.gw1.exception.InvalidAmountException;
import com.nhnacademy.gw1.exception.SmsNotifyFailedException;
import com.nhnacademy.gw1.notification.AppPushNotification;
import com.nhnacademy.gw1.notification.EmailNotification;
import com.nhnacademy.gw1.notification.SmsNotification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class PaymentServiceTest {
    // SUT
    PaymentService service;
    // DOC
    CustomerRepository repository;
    long amount;
    Long customerId;

    Customer customer;

    @BeforeEach
    void setUp() {

        repository = mock(CustomerRepository.class);
        service = new PaymentService(repository);

        amount = 10_000L;
        customerId = 12345L;

        customer = new Customer(customerId, "customerName");
        customer.setEmailAddress("email@nhnacademy.com");
        customer.setSmsNumber("010-1111-1111");
    }

    @Test
    @DisplayName("계정이 없으면 예외 발생")
    void pay_notFoundCustomer_thenThrowCustomerNotFoundException() {

        when(repository.findById(customerId)).thenReturn(null);

        assertThatThrownBy(() -> service.pay(amount, customerId)).isInstanceOf(CustomerNotFoundException.class).hasMessageContaining("Not found customer", customerId.toString());
    }


    @Test
    @DisplayName("결제금액이 유효해야함")
    void pay_invalidAmount_thenThrowInvalidAmountException() {

        long amount = -10_000L;

        when(repository.findById(customerId)).thenReturn(customer);

        assertThatThrownBy(() -> service.pay(amount, customerId)).isInstanceOf(InvalidAmountException.class).hasMessageContaining("Invalid amount", customerId.toString());
    }

    @Test
    @DisplayName("실 결제 금액을 기준으로 적립율에 따라서 적립됨")
    void pay_successPay_then_givePoints() {

        when(repository.findById(customerId)).thenReturn(customer);

        service.pay(amount, customerId);

        // 적립금은 결재 금액의 10% 부여
        assertThat(customer.getPoints()).isEqualTo(1_000L);
    }

    @Test
    @DisplayName("결제 완료 후 영수증 반환")
    void receiptReturn_Test() {

        when(repository.findById(customerId)).thenReturn(customer);

        Receipt receipt = service.pay(amount, customerId);

        //결제 성공시 영수증 반환
        assertThat(receipt).isNotNull();

        assertThat(receipt.getCustomer().getCustomerId()).isEqualTo(customerId);
        assertThat(receipt.getTotalAmount()).isEqualTo(amount);
    }

    @Test
    @DisplayName("결제 시 적립금을 사용할 수 있음")
    void pay_usingPoints() {

        when(repository.findById(customerId)).thenReturn(customer);

        // customeer의 적립금사용여부 ; true
        // 적릭금 ; 1_000L
        customer.setIntentionUsePoint(true);
        customer.increasePoints(1_000L);

        Receipt result = service.pay(amount, customerId);
        assertThat(result.getActualAmount()).isEqualTo(9000L);
    }

    @Test
    @DisplayName("적립금 사용 않는 경우")
    void pay_notUsePoints() {

        when(repository.findById(customerId)).thenReturn(customer);

        // customeer의 적립금사용여부 ; true
        // 기존 적릭금 ; 1_000L
        customer.setIntentionUsePoint(false);
        customer.increasePoints(1_000L);

        Receipt result = service.pay(amount, customerId);
        assertThat(customer.getPoints()).isEqualTo(2000L);
    }

    @Test
    @DisplayName("Sms 알람 성공시 알람 발생")
    void pay_successPay_then_SmsAlertPaymentMessage() {

        when(repository.findById(customerId)).thenReturn(customer);

        Receipt receipt = service.pay(amount, customerId);

        // 결제 성공시 SMS 알림 발생
        // fake로 구현
        SmsNotification smsAlertService = new SmsNotification();
        assertThat(smsAlertService.messageNotify(receipt)).isEqualTo(true);
    }

    @Test
    @DisplayName("Sms 알림 발송 실패지만 결제는 성공")
    void pay_failSmsAlertPaymentMessage_but_successPay() {
        customer.setSmsNumber(null);

        when(repository.findById(customerId)).thenReturn(customer);

        Receipt receipt = service.pay(amount, customerId);

        // 결제 성공시 영수증 반환
        assertThat(receipt).isNotNull();
    }

    @Test
    @DisplayName("Email 알람 성공시 알람 발생")
    void pay_successPay_then_emailAlertPaymentMessage() {

        when(repository.findById(customerId)).thenReturn(customer);

        Receipt receipt = service.pay(amount, customerId);

        // 결제 성공시 Email 알림 발생
        // fake로 구현
        EmailNotification emailAlertService = new EmailNotification();
        assertThat(emailAlertService.messageNotify(receipt)).isEqualTo(true);
    }


    @Test
    @DisplayName("Email 알림 발송 실패지만 결제는 성공")
    void pay_failEmailAlertPaymentMessage_but_successPay() {

        when(repository.findById(customerId)).thenReturn(customer);
        customer.setEmailAddress(null);

        Receipt receipt = service.pay(amount, customerId);
        //결제 성공시 영수증 반환
        assertThat(receipt).isNotNull();
    }

    @Test
    @DisplayName("AppPush 알람 성공시 알람 발생")
    void pay_successPay_then_AppPushAlertPaymentMessage() {

        when(repository.findById(customerId)).thenReturn(customer);

        Receipt receipt = service.pay(amount, customerId);

        // 결제 성공시 AppPush 알림 발생
        // fake로 구현
        AppPushNotification appPushNotification = new AppPushNotification();
        assertThat(appPushNotification.messageNotify(receipt)).isEqualTo(true);
    }


}