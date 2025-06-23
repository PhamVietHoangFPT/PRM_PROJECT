package com.datj.mobile.data.remote.api;

import com.datj.mobile.data.remote.model.Login;
import com.datj.mobile.data.remote.model.LoginGoogle;
import com.datj.mobile.data.remote.model.Register;
import com.datj.mobile.data.remote.response.AccountResponse;
import com.datj.mobile.data.remote.response.LoginResponse;
import com.datj.mobile.data.remote.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AccountApiService {
    @POST("api/Authentication/login")
    Call<LoginResponse> login(@Body Login loginAccount);

    @GET("api/Accounts/me")
    Call<AccountResponse> getProfile();
    @POST("api/Authentication/register/1")
    Call<RegisterResponse> register(@Body Register request);
    @POST("api/Accounts/login-google") // Giả định endpoint
    Call<LoginResponse> loginWithGoogle(@Body LoginGoogle request);
}
