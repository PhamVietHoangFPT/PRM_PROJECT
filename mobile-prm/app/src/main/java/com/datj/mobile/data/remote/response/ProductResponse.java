package com.datj.mobile.data.remote.response;

import com.datj.mobile.data.remote.model.Product;

public class ProductResponse {
    private Product product;
    private String status;
    private String message;

    // Getters and Setters
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
