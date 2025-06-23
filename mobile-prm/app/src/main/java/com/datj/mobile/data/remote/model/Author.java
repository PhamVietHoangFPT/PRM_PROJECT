package com.datj.mobile.data.remote.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

import java.util.List;

public class Author {
    private int accountId;
    private int role;
    private int rankId;
    private Rank rank; // Thêm rank vào đây
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String birthday;
    private int gender;
    private int rewardPoint;
    private String createdAt;
    private List<Object> ordersOfCustomer;
    private List<Object> ordersOfSaleStaff;
    private List<Object> ordersOfDeliveryStaff;
    private List<Object> warrantyRequestsOfCustomer;
    private List<Object> warrantyRequestsOfWarrantyStaff;
    private List<Object> warrantyRequestsOfDeliveryStaff;
    private List<Object> blogs;
    private Object feedbacks;
    private Object priceRates;

    public Author() {
    }

    public Author(int accountId, int role, int rankId, Rank rank, String name, String email, String password, String phoneNumber, String address, String birthday, int gender, int rewardPoint, String createdAt, List<Object> ordersOfCustomer, List<Object> ordersOfSaleStaff, List<Object> ordersOfDeliveryStaff, List<Object> warrantyRequestsOfCustomer, List<Object> warrantyRequestsOfWarrantyStaff, List<Object> warrantyRequestsOfDeliveryStaff, List<Object> blogs, Object feedbacks, Object priceRates) {
        this.accountId = accountId;
        this.role = role;
        this.rankId = rankId;
        this.rank = rank;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birthday = birthday;
        this.gender = gender;
        this.rewardPoint = rewardPoint;
        this.createdAt = createdAt;
        this.ordersOfCustomer = ordersOfCustomer;
        this.ordersOfSaleStaff = ordersOfSaleStaff;
        this.ordersOfDeliveryStaff = ordersOfDeliveryStaff;
        this.warrantyRequestsOfCustomer = warrantyRequestsOfCustomer;
        this.warrantyRequestsOfWarrantyStaff = warrantyRequestsOfWarrantyStaff;
        this.warrantyRequestsOfDeliveryStaff = warrantyRequestsOfDeliveryStaff;
        this.blogs = blogs;
        this.feedbacks = feedbacks;
        this.priceRates = priceRates;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(int rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<Object> getOrdersOfCustomer() {
        return ordersOfCustomer;
    }

    public void setOrdersOfCustomer(List<Object> ordersOfCustomer) {
        this.ordersOfCustomer = ordersOfCustomer;
    }

    public List<Object> getOrdersOfSaleStaff() {
        return ordersOfSaleStaff;
    }

    public void setOrdersOfSaleStaff(List<Object> ordersOfSaleStaff) {
        this.ordersOfSaleStaff = ordersOfSaleStaff;
    }

    public List<Object> getOrdersOfDeliveryStaff() {
        return ordersOfDeliveryStaff;
    }

    public void setOrdersOfDeliveryStaff(List<Object> ordersOfDeliveryStaff) {
        this.ordersOfDeliveryStaff = ordersOfDeliveryStaff;
    }

    public List<Object> getWarrantyRequestsOfCustomer() {
        return warrantyRequestsOfCustomer;
    }

    public void setWarrantyRequestsOfCustomer(List<Object> warrantyRequestsOfCustomer) {
        this.warrantyRequestsOfCustomer = warrantyRequestsOfCustomer;
    }

    public List<Object> getWarrantyRequestsOfWarrantyStaff() {
        return warrantyRequestsOfWarrantyStaff;
    }

    public void setWarrantyRequestsOfWarrantyStaff(List<Object> warrantyRequestsOfWarrantyStaff) {
        this.warrantyRequestsOfWarrantyStaff = warrantyRequestsOfWarrantyStaff;
    }

    public List<Object> getWarrantyRequestsOfDeliveryStaff() {
        return warrantyRequestsOfDeliveryStaff;
    }

    public void setWarrantyRequestsOfDeliveryStaff(List<Object> warrantyRequestsOfDeliveryStaff) {
        this.warrantyRequestsOfDeliveryStaff = warrantyRequestsOfDeliveryStaff;
    }

    public List<Object> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Object> blogs) {
        this.blogs = blogs;
    }

    public Object getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Object feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Object getPriceRates() {
        return priceRates;
    }

    public void setPriceRates(Object priceRates) {
        this.priceRates = priceRates;
    }
    // Getters and Setters
}


