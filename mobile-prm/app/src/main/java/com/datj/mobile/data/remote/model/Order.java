package com.datj.mobile.data.remote.model;

import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("orderId")
    private long orderId;

    @SerializedName("status")
    private String status; // "Pending", "Processing", "Confirmed", "Delivering", "Completed", "Failed"

    @SerializedName("createdAt")
    private String createdAt;

    // Getters
    public long getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}