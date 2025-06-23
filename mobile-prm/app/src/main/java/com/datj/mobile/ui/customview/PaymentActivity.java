package com.datj.mobile.ui.customview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.datj.mobile.R;
import com.datj.mobile.data.local.TokenManager;
import com.datj.mobile.data.remote.RetrofitClient;
import com.datj.mobile.data.remote.api.OrderApiService;
import com.datj.mobile.data.remote.response.CaptureResponse;
import com.datj.mobile.data.remote.resquest.CapturePaypalOrderRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentActivity extends AppCompatActivity {
    private OrderApiService orderApi;
    private String bearerToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bearerToken = "Bearer " + TokenManager.getInstance().getToken();
        orderApi = RetrofitClient.getOrderApiService();

        handlePaypalReturn();
    }

    private void handlePaypalReturn() {
        Uri data = getIntent().getData();
        if (data != null && data.toString().startsWith("com.datj.mobile://paypalpay")) {
            String orderId = data.getQueryParameter("orderId");

            if (orderId != null) {
                capturePaypalOrder(orderId);
            }
        }
    }

    private void capturePaypalOrder(String orderId) {
        CapturePaypalOrderRequest req = new CapturePaypalOrderRequest(orderId);
        orderApi.capturePaypalOrder(req).enqueue(new Callback<CaptureResponse>() {
            @Override
            public void onResponse(Call<CaptureResponse> call, Response<CaptureResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String referenceId = response.body().getPurchaseUnits().get(0).getReferenceId(); // REF-xxx
                    String transactionId = referenceId;
                    String realOrderId = referenceId.substring(4); // Xoá "REF-"

                    completeTransaction(transactionId, realOrderId);
                }
            }

            @Override
            public void onFailure(Call<CaptureResponse> call, Throwable t) {
                Log.e("PayPal", "Capture failed: " + t.getMessage());
            }
        });
    }

    private void completeTransaction(String transactionId, String orderId) {
        orderApi.completeTransaction(transactionId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    completeOrder(orderId);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("PayPal", "Complete transaction error: " + t.getMessage());
            }
        });
    }

    private void completeOrder(String orderId) {
        orderApi.completeOrder(orderId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PaymentActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("PayPal", "Complete order error: " + t.getMessage());
            }
        });
    }
}
