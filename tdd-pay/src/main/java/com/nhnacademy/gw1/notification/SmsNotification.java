package com.nhnacademy.gw1.notification;

import com.nhnacademy.gw1.Receipt;
import com.nhnacademy.gw1.exception.SmsNotifyFailedException;

public class SmsNotification implements Notification {

    @Override
    public boolean messageNotify(Receipt receipt) {
        if (receipt.getCustomer().getSmsNumber() != null) {
            System.out.println(getClass().getSimpleName() + ".messageNotify() is called.");
            return true;
        }
        throw new SmsNotifyFailedException(receipt.getCustomer().getCustomerId());
    }
}
