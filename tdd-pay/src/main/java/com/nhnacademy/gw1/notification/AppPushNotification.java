package com.nhnacademy.gw1.notification;

import com.nhnacademy.gw1.Receipt;

public class AppPushNotification implements Notification {
    @Override
    public boolean messageNotify(Receipt receipt) {
        System.out.println(getClass().getSimpleName() + ".messageNotify() is called.");
        return true;
    }
}
