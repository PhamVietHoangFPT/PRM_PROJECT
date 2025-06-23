package com.datj.mobile.data.remote.resquest;

public class CapturePaypalOrderRequest {
    private String orderId;

    private String transactionId;
    public CapturePaypalOrderRequest(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
