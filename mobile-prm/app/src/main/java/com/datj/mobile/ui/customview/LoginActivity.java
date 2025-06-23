package com.datj.mobile.ui.customview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import com.datj.mobile.R;
import com.datj.mobile.data.local.TokenManager;
import com.datj.mobile.data.remote.model.LoginGoogle;
import com.datj.mobile.data.remote.response.LoginResponse;
import com.datj.mobile.databinding.ActivityLoginBinding;
import com.datj.mobile.ui.main.MainActivity;
import com.datj.mobile.viewmodel.LoginGoogleViewModel;
import com.datj.mobile.viewmodel.LoginViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private GoogleSignInClient googleSignInClient;
    private LoginViewModel viewModel;
    private LoginGoogleViewModel loginGGviewModel;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Nếu đã có token thì tự động chuyển vào MainActivity
        String token = TokenManager.getInstance().getToken();
        if (token != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // không cho quay lại Login
            return;
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.toastMessage.observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
        binding.tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        viewModel.getLoginResult().observe(this, loginResponse -> {
            if (loginResponse != null && loginResponse.getToken() != null) {
                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("TOKEN", loginResponse.getToken());
                startActivity(intent);
                finish();
            }
        });
    }
    private void setupGoogleSignIn() {
        // Cấu hình Google Sign-In để chỉ yêu cầu email
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void setupListeners() {
        // Sự kiện click cho nút Google Login
        binding.btnLoginGoogle.setOnClickListener(v -> {
            Log.d("GoogleLogin", "btnLoginGoogle clicked"); // Debug
            signInWithGoogle();
        });

        binding.tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Các listener khác (nếu có, như nút Login thông thường)
    }

    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    String email = account.getEmail();
                    Log.d("GoogleLogin", "Email: " + email);
                    loginWithGoogle(email);
                }
            } catch (ApiException e) {
                Log.e("GoogleLogin", "Google Sign-In failed: " + e.getStatusCode());
                Toast.makeText(this, "Đăng nhập Google thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loginWithGoogle(String email) {
        LoginGoogle request = new LoginGoogle(email);
        loginGGviewModel.loginWithGoogle(request);
    }
    private void observeLoginResponse() {
        loginGGviewModel.getLoginResponse().observe(this, loginResponse -> {
            if (loginResponse != null) {
                String token = loginResponse.getToken();
                TokenManager.getInstance().saveToken(token);
                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("access_token", token); // token là chuỗi bạn lấy từ response
                editor.apply();
                Log.d("GoogleLogin", "Login successful, token: " + token);
                Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}