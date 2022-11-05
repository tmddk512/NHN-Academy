package com.nhnacademy.gw1;

import com.nhnacademy.gw1.exception.CustomerNotFoundException;
import com.nhnacademy.gw1.exception.EmailNotifyFailedException;
import com.nhnacademy.gw1.exception.InvalidAmountException;
import com.nhnacademy.gw1.exception.SmsNotifyFailedException;
import com.nhnacademy.gw1.notification.AppPushNotification;
import com.nhnacademy.gw1.notification.EmailNotification;
import com.nhnacademy.gw1.notification.SmsNotification;

public class PaymentService {
    private final CustomerRepository customerRepository;

    public PaymentService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * 결제처리
     *
     * @param amount     결재 금액
     * @param customerId 고객 아이디
     * @return 영수증
     */
    public Receipt pay(long amount, Long customerId) {
        Customer customer = customerRepository.findById(customerId);

        if (customer == null) {
            throw new CustomerNotFoundException(customerId);
        }
        if (amount < 0) {
            throw new InvalidAmountException(amount);
        }

        long actualAmount = amount;

        if (customer.getIntentionUsePoint()) {
            // customer의 적립금을 불러온 후 적립금 차감(사용)
            long points = customer.getPoints();
            customer.decreasePoints(points);

            // 실결재금액은 적립금을 제외한 금액
            actualAmount -= points;
        }

        customer.increasePoints(accumulatePoint(actualAmount));

        Receipt receipt = new Receipt(customer, amount, actualAmount);

        messageNotify(receipt);

        return receipt;
    }

    private static void messageNotify(Receipt receipt) {
        try {
            // Sms, Email, AppPush 알람 전송
            SmsNotification smsNotification = new SmsNotification();
            smsNotification.messageNotify(receipt);

            EmailNotification emailNotification = new EmailNotification();
            emailNotification.messageNotify(receipt);

            AppPushNotification appPushNotification = new AppPushNotification();
            appPushNotification.messageNotify(receipt);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public long accumulatePoint(long amount) {
        return (long) (amount * 0.1);
    }
}
