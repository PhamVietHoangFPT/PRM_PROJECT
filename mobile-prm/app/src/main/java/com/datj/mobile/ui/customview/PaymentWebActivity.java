package com.datj.mobile.ui.customview;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.datj.mobile.R;

public class PaymentWebActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_web);

        webView = findViewById(R.id.webView);

        // Nhận URL từ Intent
        String paymentUrl = getIntent().getStringExtra("payment_url");
        if (paymentUrl == null || paymentUrl.isEmpty()) {
            Toast.makeText(this, "Không có URL thanh toán", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // ✅ Nếu URL chứa cụm `payment-success`, xem như thanh toán thành công
                if (url.contains("payment-success")) {
                    Intent intent = new Intent(PaymentWebActivity.this, PaymentSuccessActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }

                // ❌ Nếu URL chứa `vnp_ResponseCode!=00`, có thể là lỗi
                if (url.contains("vnp_ResponseCode=")) {
                    String code = url.split("vnp_ResponseCode=")[1].substring(0, 2);
                    if (!code.equals("00")) {
                        Toast.makeText(PaymentWebActivity.this, "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
                        finish();
                        return true;
                    }
                }

                return false;
            }
        });

        webView.loadUrl(paymentUrl);
    }
}
