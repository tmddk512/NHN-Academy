package com.nhnacademy.gw1.notification;

import com.nhnacademy.gw1.Receipt;

public interface Notification {
    boolean messageNotify(Receipt receipt);
}
