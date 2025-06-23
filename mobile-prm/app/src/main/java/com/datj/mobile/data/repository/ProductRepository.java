package com.datj.mobile.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.datj.mobile.data.remote.api.ProductApiService;
import com.datj.mobile.data.remote.model.Product;
import com.datj.mobile.data.remote.response.ProductResponse;
import com.datj.mobile.data.network.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private ProductApiService apiService;

    public ProductRepository() {
        apiService = ApiClient.getClient().create(ProductApiService.class);
    }

    public LiveData<Product> getProductByAccessoryId(int accessoryId) {
        MutableLiveData<Product> data = new MutableLiveData<>();

        apiService.getProductById(accessoryId).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_SUCCESS", "Product Data: " + response.body().getProduct().getName());
                    data.setValue(response.body().getProduct());
                } else {
                    Log.e("API_ERROR", "Response unsuccessful or empty body");
                }
            }
            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("API_FAILURE", "API call failed: " + t.getMessage());
                data.setValue(null);
            }
        });

        return data;
    }


    public LiveData<List<Product>> getAllProducts() {
        MutableLiveData<List<Product>> data = new MutableLiveData<>();

        apiService.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                data.setValue(new ArrayList<>()); // Trả về danh sách rỗng thay vì null
            }
        });

        return data;
    }



}
