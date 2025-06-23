package com.datj.mobile.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.datj.mobile.data.local.TokenManager;
import com.datj.mobile.data.remote.response.LoginResponse;
import com.datj.mobile.data.repository.AuthRepository;

public class LoginViewModel extends AndroidViewModel {
    public MutableLiveData<String> userEmail = new MutableLiveData<>("");
    public MutableLiveData<String> userPassword = new MutableLiveData<>("");
    public MutableLiveData<String> toastMessage = new MutableLiveData<>("");
    private final AuthRepository authRepository;
    private final MutableLiveData<LoginResponse> loginResult = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.authRepository = new AuthRepository(); // Không cần Context nữa
    }

    public LiveData<LoginResponse> getLoginResult() {
        return loginResult;
    }

    // Hàm xử lý khi nhấn nút đăng nhập
    public void onButtonClicked() {
        String email = userEmail.getValue();
        String password = userPassword.getValue();

        if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
            login(email, password); // Gọi hàm login
        } else {
            toastMessage.setValue("Please enter email and password");
        }
    }

    public void login(String email, String password) {
        authRepository.login(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(String token) {
                // Chuyển sang luồng chính để cập nhật LiveData
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    loginResult.setValue(new LoginResponse(token));
                    TokenManager.getInstance().decodeAndSaveRole(token); // đã có trong code bạn
                    toastMessage.setValue("Login successful with email: " + email);
                });
            }

            @Override
            public void onError(String message) {
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    loginResult.setValue(null);
                    toastMessage.setValue(message);
                });
            }
        });
    }
}