package com.datj.mobile.data.remote.resquest;

public class TransactionRequest {
    private String orderId;
    private String paymentMethod;
    private double amount;

    public TransactionRequest(String orderId, String paymentMethod, double amount) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }

    // Getters
    public String getOrderId() { return orderId; }
    public String getPaymentMethod() { return paymentMethod; }
    public double getAmount() { return amount; }
}