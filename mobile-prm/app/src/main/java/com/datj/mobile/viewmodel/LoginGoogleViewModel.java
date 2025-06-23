package com.datj.mobile.viewmodel;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.datj.mobile.data.remote.RetrofitClient;
import com.datj.mobile.data.remote.api.AccountApiService;
import com.datj.mobile.data.remote.model.LoginGoogle;
import com.datj.mobile.data.remote.response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginGoogleViewModel extends ViewModel {
    private MutableLiveData<LoginResponse> loginResponse = new MutableLiveData<>();
    private AccountApiService accountApiService;

    public LiveData<LoginResponse> getLoginResponse() {
        return loginResponse;
    }

    public void loginWithGoogle(LoginGoogle request) {
        accountApiService.loginWithGoogle(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    loginResponse.setValue(response.body());
                    Log.d("GoogleLogin", "Success: " + response.body().getToken());
                } else {
                    loginResponse.setValue(null);
                    Log.e("GoogleLogin", "Error: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginResponse.setValue(null);
                Log.e("GoogleLogin", "Failure: " + t.getMessage());
            }
        });
    }
    public LoginGoogleViewModel() {
        accountApiService = RetrofitClient.getAccountApiService();
    }
}
