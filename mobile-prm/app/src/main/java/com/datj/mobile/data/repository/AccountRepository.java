package com.datj.mobile.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.datj.mobile.data.local.TokenManager;
import com.datj.mobile.data.remote.RetrofitClient;
import com.datj.mobile.data.remote.api.AccountApiService;
import com.datj.mobile.data.remote.response.AccountResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccountRepository {
    private final AccountApiService accountApiService;
    private final TokenManager tokenManager;

    public AccountRepository() {
        accountApiService = RetrofitClient.getAccountApiService();
        this.tokenManager = TokenManager.getInstance();
    }

    public LiveData<AccountResponse> getAccount() {
        MutableLiveData<AccountResponse> profileData = new MutableLiveData<>();

        String token = tokenManager.getToken();
        if (token == null) {
            profileData.setValue(null);
            return profileData;
        }

        accountApiService.getProfile().enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    profileData.setValue(response.body());
                } else {
                    profileData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                profileData.setValue(null);
            }
        });

        return profileData;
    }
}