package com.datj.mobile.data.remote.resquest;

public class CreatePaypalOrderRequest {
    private String reference;
    private String orderId;

    public CreatePaypalOrderRequest(String reference, String orderId) {
        this.reference = reference;
        this.orderId = orderId;
    }
}