package com.datj.mobile.data.remote.api;

import com.datj.mobile.data.remote.model.Accessory;
import com.datj.mobile.data.remote.response.AccessoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AccessoryApiService {
    @GET("api/Accessories")
    Call<AccessoryResponse> getAccessories(
            @Query("PageSize") int pageSize,
            @Query("PageNumber") int pageNumber
    );
    @GET("api/Accessories/1/{id}")
    Call<Accessory> getAccessoryById(@Path("id") int id);
}