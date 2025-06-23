package com.datj.mobile.ui.customview;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.datj.mobile.data.local.TokenManager;
import com.datj.mobile.data.remote.RetrofitClient;
import com.datj.mobile.data.remote.api.AccountApiService;
import com.datj.mobile.data.remote.model.Register;
import com.datj.mobile.data.remote.response.RegisterResponse;
import com.datj.mobile.databinding.ActivityRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private AccountApiService accountApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        accountApiService = RetrofitClient.getAccountApiService();
        setupGenderSpinner();
        setupRegisterButton();
    }

    private void setupGenderSpinner() {
        String[] genders = {"Male", "Female", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genders);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.genderSpinner.setAdapter(adapter);
    }

    private void setupRegisterButton() {
        binding.registerButton.setOnClickListener(v -> register());
    }

    private void register() {
        String name = binding.nameEditText.getText().toString().trim();
        String phoneNumber = binding.phoneEditText.getText().toString().trim();
        String address = binding.addressEditText.getText().toString().trim();
        String email = binding.emailEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();
        String birthday = binding.birthdayEditText.getText().toString().trim();
        String gender = binding.genderSpinner.getSelectedItem().toString();

        if (name.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || email.isEmpty() || password.isEmpty() || birthday.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        Register request = new Register(name, phoneNumber, address, email, password, birthday, gender);
        accountApiService.register(request).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();
                    TokenManager.getInstance().saveToken(token);
                    Log.d("RegisterDebug", "Registration successful, token: " + token);
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    finish(); // Quay lại màn hình trước (thường là Login)
                } else {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e("RegisterDebug", "Error: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("RegisterDebug", "Failure: " + t.getMessage());
            }
        });
    }
}
