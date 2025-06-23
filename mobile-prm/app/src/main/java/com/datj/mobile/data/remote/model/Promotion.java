package com.datj.mobile.data.remote.model;

public class Promotion {
    private int promotionId;
    private String promotionName;
    private String promotionCode;
    private double discountPercent;
    private String startTime;
    private String endTime;

    public int getPromotionId() {
        return promotionId;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}