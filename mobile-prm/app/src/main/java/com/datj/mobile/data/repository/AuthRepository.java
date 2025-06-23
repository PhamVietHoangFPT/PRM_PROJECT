package com.datj.mobile.data.repository;

import android.content.Context;
import com.datj.mobile.data.local.TokenManager;
import com.datj.mobile.data.remote.RetrofitClient;
import com.datj.mobile.data.remote.api.AccountApiService;
import com.datj.mobile.data.remote.model.Login;
import com.datj.mobile.data.remote.response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private final AccountApiService accountApiService;
    private final TokenManager tokenManager;

    public AuthRepository() {
        accountApiService = RetrofitClient.getAccountApiService();
        this.tokenManager = TokenManager.getInstance();
    }

    public void login(String email, String password, AuthCallback callback) {
        Login loginAccount = new Login(email, password);
        Call<LoginResponse> call = accountApiService.login(loginAccount);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null) {
                    String token = response.body().getToken();
                    if (token != null) {
                        tokenManager.decodeAndSaveRole(token);
                        callback.onSuccess(token);
                    } else {
                        callback.onError("Token is null or invalid");
                    }
                } else {
                    callback.onError("Login failed! Please check your email or password.");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.onError("Connection error: " + t.getMessage());
            }
        });
    }

    public interface AuthCallback {
        void onSuccess(String token);
        void onError(String errorMessage);
    }
}
