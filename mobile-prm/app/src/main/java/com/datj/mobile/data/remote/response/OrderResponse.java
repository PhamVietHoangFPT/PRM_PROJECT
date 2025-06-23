package com.datj.mobile.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class OrderResponse {
    @SerializedName("orderId")
    private String orderId;
    private double totalPrice;
    private double totalDiscountPercent;

    public String getOrderId() { return orderId; }
    public double getTotalPrice() { return totalPrice; }
    public double getTotalDiscountPercent() { return totalDiscountPercent; }

    public void setTotalDiscountPercent(double totalDiscountPercent) {
        this.totalDiscountPercent = totalDiscountPercent;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}