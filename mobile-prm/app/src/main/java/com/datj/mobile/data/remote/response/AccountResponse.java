package com.datj.mobile.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccountResponse {
    @SerializedName("accountId")
    private int accountId;

    @SerializedName("role")
    private String role;

    @SerializedName("rank")
    private Rank rank;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("address")
    private String address;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("gender")
    private String gender;

    @SerializedName("rewardPoint")
    private int rewardPoint;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("ordersOfCustomer")
    private List<Object> ordersOfCustomer;

    @SerializedName("ordersOfSaleStaff")
    private List<Object> ordersOfSaleStaff;

    @SerializedName("ordersOfDeliveryStaff")
    private List<Object> ordersOfDeliveryStaff;

    @SerializedName("warrantyRequestsOfCustomer")
    private List<Object> warrantyRequestsOfCustomer;

    @SerializedName("warrantyRequestsOfWarrantyStaff")
    private List<Object> warrantyRequestsOfWarrantyStaff;

    @SerializedName("warrantyRequestsOfDeliveryStaff")
    private List<Object> warrantyRequestsOfDeliveryStaff;

    @SerializedName("totalOrders")
    private int totalOrders;

    // Class Rank lồng bên trong
    public static class Rank {
        @SerializedName("rankId")
        private int rankId;

        @SerializedName("rankName")
        private String rankName;

        @SerializedName("discount")
        private double discount;

        @SerializedName("rewardPoint")
        private int rewardPoint;

        public int getRankId() {
            return rankId;
        }

        public String getRankName() {
            return rankName;
        }

        public double getDiscount() {
            return discount;
        }

        public int getRewardPoint() {
            return rewardPoint;
        }
    }

    // Getters
    public int getAccountId() {
        return accountId;
    }

    public String getRole() {
        return role;
    }

    public Rank getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public int getRewardPoint() {
        return rewardPoint;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public List<Object> getOrdersOfCustomer() {
        return ordersOfCustomer;
    }

    public List<Object> getOrdersOfSaleStaff() {
        return ordersOfSaleStaff;
    }

    public List<Object> getOrdersOfDeliveryStaff() {
        return ordersOfDeliveryStaff;
    }

    public List<Object> getWarrantyRequestsOfCustomer() {
        return warrantyRequestsOfCustomer;
    }

    public List<Object> getWarrantyRequestsOfWarrantyStaff() {
        return warrantyRequestsOfWarrantyStaff;
    }

    public List<Object> getWarrantyRequestsOfDeliveryStaff() {
        return warrantyRequestsOfDeliveryStaff;
    }

    public int getTotalOrders() {
        return totalOrders;
    }
}