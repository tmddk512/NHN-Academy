package com.nhnacademy.gw1;

public class Receipt {
    Customer customer;
    long totalAmount;
    private long actualAmount;

    public Receipt(Customer customer, long totalAmount, long actualAmount) {
        this.customer = customer;
        this.totalAmount = totalAmount;
        this.actualAmount = actualAmount;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public long getActualAmount(){
        return this.actualAmount;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "customer=" + customer +
                ", totalAmount=" + totalAmount +
                ", actualAmount=" + actualAmount +
                '}';
    }
}
