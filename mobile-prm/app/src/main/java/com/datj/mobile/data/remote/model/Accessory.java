package com.datj.mobile.data.remote.model;

import java.io.Serializable;
import java.util.List;

public class Accessory implements Serializable {
    private int accessoryId;
    private int karat;
    private double materialWeight;
    private String name;
    private double price;
    private int quantity;
    private AccessoryType accessoryType;
    private boolean isHidden;
    private Shape shape;
    private List<AccessoryImage> accessoryImages;

    public int getAccessoryId() { return accessoryId; }
    public String getName() { return name; }
    public List<AccessoryImage> getAccessoryImages() { return accessoryImages; }
    public AccessoryType getAccessoryType() {
        return accessoryType;
    }

    public int getKarat() {
        return karat;
    }

    public double getMaterialWeight() {
        return materialWeight;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public Shape getShape() {
        return shape;
    }

    public double getPrice() {
        return price;
    }
    public String getFirstImageUrl() {
        if (accessoryImages != null && !accessoryImages.isEmpty()) {
            return accessoryImages.get(0).getUrl();
        }
        return ""; // hoặc null nếu bạn muốn kiểm tra sau
    }
}
