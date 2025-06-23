package com.datj.mobile.data.remote.api;

import com.datj.mobile.data.remote.model.CartCountResponse;
import com.datj.mobile.data.remote.model.CartItem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CartApiService {
    // Gửi giỏ hàng lên server
    @POST("/cart/add")
    Call<Void> addCartItem(@Body CartItem cartItem);

    // Lấy tổng số lượng từ server (nếu cần)
    @GET("/cart/count")
    Call<CartCountResponse> getCartCount();
}

