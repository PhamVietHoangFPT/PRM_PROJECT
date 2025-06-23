package com.datj.mobile.data.remote.api;

import com.datj.mobile.data.remote.model.Product;
import com.datj.mobile.data.remote.response.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductApiService {

    @GET("Accessories") // Đường dẫn API có thể thay đổi theo backend
    Call<List<Product>> getAllProducts();
    @GET("Accessories/{accessoryId}")
    Call<ProductResponse> getProductById(@Path("accessoryId") int accessoryId);
}