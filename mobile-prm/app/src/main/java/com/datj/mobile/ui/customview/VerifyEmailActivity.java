package com.datj.mobile.ui.customview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.datj.mobile.ui.main.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class VerifyEmailActivity extends AppCompatActivity {
    private static final String VERIFY_EMAIL_API = "http://localhost:8080/api/Authentication/verify-gmail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Lấy token từ Deep Link
        Uri data = getIntent().getData();
        if (data != null && data.getQueryParameter("token") != null) {
            String token = data.getQueryParameter("token");

            // Gửi token đến API để xác nhận email
            verifyEmail(token);
        } else {
            Toast.makeText(this, "Không tìm thấy token", Toast.LENGTH_SHORT).show();
        }
    }

    private void verifyEmail(String token) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, VERIFY_EMAIL_API,
                response -> {
                    Toast.makeText(this, "Xác nhận email thành công!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                },
                error -> {
                    Toast.makeText(this, "Xác nhận email thất bại!", Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("token", token);
                return params;
            }
        };

        queue.add(stringRequest);
    }
}
