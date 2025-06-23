package com.datj.mobile.data.remote.resquest;

import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.PurchaseUnit;

import java.util.ArrayList;
import java.util.List;

public class OrderRequest {
    private List<OrderDetailRequest> orderDetails;
    private String shippingAddress;
    private String phoneNumber;
    private String promotionCode;

    public OrderRequest(List<OrderDetailRequest> orderDetails, String shippingAddress, String phoneNumber, String promotionCode) {
        this.orderDetails = orderDetails;
        this.shippingAddress = shippingAddress;
        this.phoneNumber = phoneNumber;
        this.promotionCode = promotionCode;
    }



    // Getters và setters nếu cần
}