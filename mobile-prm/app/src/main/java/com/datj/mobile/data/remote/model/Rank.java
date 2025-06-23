package com.datj.mobile.data.remote.model;

import java.util.List;

public class Rank {
    private int rankId;
    private String rankName;
    private int discount;
    private int rewardPoint;
    private List<Object> accounts;
    private List<Object> orders;

    // Getters and Setters

    public Rank() {
    }

    public Rank(int rankId, String rankName, int discount, int rewardPoint, List<Object> accounts, List<Object> orders) {
        this.rankId = rankId;
        this.rankName = rankName;
        this.discount = discount;
        this.rewardPoint = rewardPoint;
        this.accounts = accounts;
        this.orders = orders;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(int rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    public List<Object> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Object> accounts) {
        this.accounts = accounts;
    }

    public List<Object> getOrders() {
        return orders;
    }

    public void setOrders(List<Object> orders) {
        this.orders = orders;
    }
}

