package com.nhnacademy.gw1.notification;

import com.nhnacademy.gw1.Customer;
import com.nhnacademy.gw1.Receipt;
import com.nhnacademy.gw1.exception.EmailNotifyFailedException;

public class EmailNotification implements Notification {
    @Override
    public boolean messageNotify(Receipt receipt) {
        if (receipt.getCustomer().getEmailAddress() != null) {
            System.out.println(getClass().getSimpleName() + ".messageNotify() is called.");
            return true;
        }
        throw new EmailNotifyFailedException(receipt.getCustomer().getCustomerId());
    }
}
