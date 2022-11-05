package com.nhnacademy.gw1;

public class Customer {
    private Long customerId;    // 아이디
    private String name;        // 이름
    private long points;        // 적립금
    private String smsNumber;
    private String emailAddress;

    private boolean intentionUsePoint;      // 적립금을 사용할지 여부

    public Customer(Long customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public long getPoints() {
        return points;
    }

    public String getSmsNumber() {
        return smsNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public boolean getIntentionUsePoint() {
        return intentionUsePoint;
    }


    public void setSmsNumber(String smsNumber) {
        this.smsNumber = smsNumber;
    }

    public void setIntentionUsePoint(boolean b) {
        this.intentionUsePoint = b;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }


    public void increasePoints(long pointAmount) {
        this.points += pointAmount;
    }

    public void decreasePoints(long points) {
        this.points -= points;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", points=" + points +
                ", smsNumber='" + smsNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", intentionUsePoint=" + intentionUsePoint +
                '}';
    }
}
