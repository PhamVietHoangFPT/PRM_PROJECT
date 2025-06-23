package com.datj.mobile.data.remote.model;

public class CartItem {
    private Accessory accessory;
    private String selectedSize;
    private int quantity;

    public CartItem(Accessory accessory, String selectedSize, int quantity) {
        this.accessory = accessory;
        this.selectedSize = selectedSize;
        this.quantity = quantity;
    }


    public Accessory getAccessory() {
        return accessory;
    }

    public String getSize() {
        return selectedSize;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setAccessory(Accessory accessory) {
        this.accessory = accessory;
    }

    public void setSize(String size) {
        this.selectedSize = size;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}