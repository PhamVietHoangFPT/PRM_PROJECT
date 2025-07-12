package com.datj.mobile.ui.customview;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.datj.mobile.R;
import com.datj.mobile.ui.main.MainActivity;

public class PaymentSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_success);

        Button btnBack = findViewById(R.id.btnBackHome);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentSuccessActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}

