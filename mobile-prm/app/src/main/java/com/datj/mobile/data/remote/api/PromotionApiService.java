package com.datj.mobile.data.remote.api;

import com.datj.mobile.data.remote.model.Promotion;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface PromotionApiService {
    @GET("api/Promotion/{code}")
    Call<Promotion> getPromotionByCode(@Path("code") String code);
}