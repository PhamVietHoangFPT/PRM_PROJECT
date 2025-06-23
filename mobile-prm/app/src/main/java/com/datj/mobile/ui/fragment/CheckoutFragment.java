package com.datj.mobile.ui.fragment;

import android.content.Intent;
import android.media.MediaRouter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datj.mobile.R;
import com.datj.mobile.data.local.CartStorage;
import com.datj.mobile.data.local.TokenManager;
import com.datj.mobile.data.remote.RetrofitClient;
import com.datj.mobile.data.remote.api.AccountApiService;
import com.datj.mobile.data.remote.api.OrderApiService;
import com.datj.mobile.data.remote.api.PromotionApiService;
import com.datj.mobile.data.remote.model.CartItem;
import com.datj.mobile.data.remote.model.Promotion;
import com.datj.mobile.data.remote.response.AccountResponse;
import com.datj.mobile.data.remote.response.PaypalOrderResponse;
import com.datj.mobile.data.remote.response.TransactionResponse;
import com.datj.mobile.data.remote.resquest.CapturePaypalOrderRequest;
import com.datj.mobile.data.remote.resquest.CreatePaypalOrderRequest;
import com.datj.mobile.data.remote.resquest.OrderDetailRequest;
import com.datj.mobile.data.remote.resquest.OrderRequest;
import com.datj.mobile.data.remote.resquest.TransactionRequest;
import com.datj.mobile.data.remote.response.OrderResponse;
import com.datj.mobile.data.remote.response.CaptureResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutFragment extends Fragment {

    private TextView tvName, tvAddress, tvDiscountInfo, tvAppliedCode, tvSubtotal, tvDiscount, tvTotal;
    private EditText edtDiscountCode;
    private Button btnApplyDiscount, btnSubmit;
    private LinearLayout discountTagLayout;
    private RecyclerView recyclerView;

    private List<CartItem> cartItems;
    private double discountPercent = 0.0;
    private double totalAmount = 0;
    private Promotion currentPromotion;

    private CheckoutItemAdapter adapter;
    private OrderApiService orderApi;
    private String bearerToken;

    private final String paypalBaseUrl = "https://www.sandbox.paypal.com/checkoutnow?token=";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);
        bindViews(view);

        cartItems = CartStorage.loadCart(requireContext());
        orderApi = RetrofitClient.getOrderApiService();
        bearerToken = "Bearer " + TokenManager.getInstance().getToken();

        setupRecyclerView();
        fetchUserInfo();
        updatePriceView();

        btnApplyDiscount.setOnClickListener(v -> {
            String code = edtDiscountCode.getText().toString().trim();
            if (!code.isEmpty()) applyDiscountCode(code);
        });

        btnSubmit.setOnClickListener(v -> startWebPaypalCheckout());

        return view;
    }

    private void bindViews(View view) {
        tvName = view.findViewById(R.id.tvName);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvDiscountInfo = view.findViewById(R.id.tvDiscountInfo);
        tvAppliedCode = view.findViewById(R.id.tvAppliedCode);
        tvSubtotal = view.findViewById(R.id.tvSubtotal);
        tvDiscount = view.findViewById(R.id.tvDiscount);
        tvTotal = view.findViewById(R.id.tvTotal);
        edtDiscountCode = view.findViewById(R.id.edtDiscountCode);
        btnApplyDiscount = view.findViewById(R.id.btnApplyDiscount);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        discountTagLayout = view.findViewById(R.id.discountTag);
        recyclerView = view.findViewById(R.id.rvCheckoutItems);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CheckoutItemAdapter(cartItems);
        recyclerView.setAdapter(adapter);
    }

    private void fetchUserInfo() {
        AccountApiService api = RetrofitClient.getAccountApiService();
        api.getProfile().enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AccountResponse user = response.body();
                    tvName.setText(user.getName());
                    tvAddress.setText(user.getAddress() + "\n" + user.getPhoneNumber());
                } else {
                    Toast.makeText(getContext(), "Failed to load user info", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void applyDiscountCode(String code) {
        PromotionApiService api = RetrofitClient.getPromotionApiService();
        api.getPromotionByCode(code).enqueue(new Callback<Promotion>() {
            @Override
            public void onResponse(Call<Promotion> call, Response<Promotion> response) {
                if (response.isSuccessful() && response.body() != null) {
                    currentPromotion = response.body();
                    discountPercent = currentPromotion.getDiscountPercent();
                    tvAppliedCode.setText(currentPromotion.getPromotionCode());
                    tvDiscountInfo.setText("Applied " + (int) (discountPercent * 100) + "% discount.");
                    discountTagLayout.setVisibility(View.VISIBLE);
                    updatePriceView();
                } else {
                    Toast.makeText(getContext(), "Invalid code", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Promotion> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePriceView() {
        double subtotal = 0;
        for (CartItem item : cartItems) {
            subtotal += item.getAccessory().getPrice() + item.getAccessory().getAccessoryType().getProcessingPrice();
        }
        double discountAmount = subtotal * discountPercent;
        double total = subtotal - discountAmount;
        totalAmount = total;
        tvSubtotal.setText("Subtotal: $" + String.format("%.2f", subtotal));
        tvDiscount.setText("Discount: -$" + String.format("%.2f", discountAmount));
        tvTotal.setText("Total: $" + String.format("%.2f", total));
    }

    private void startWebPaypalCheckout() {
        List<OrderDetailRequest> detailRequests = new ArrayList<>();
        for (CartItem item : cartItems) {
            int accessoryId = item.getAccessory() != null ? item.getAccessory().getAccessoryId() : -1;
            float parsedSize = parseSize(item.getSize());

            if (accessoryId > 0) {
                detailRequests.add(new OrderDetailRequest(parsedSize, accessoryId));
            } else {
                Log.e("Checkout", "Accessory null hoặc ID không hợp lệ");
            }
        }

        String[] parts = tvAddress.getText().toString().split("\n");
        String address = parts.length > 0 ? parts[0] : "";
        String phone = parts.length > 1 ? parts[1] : "";

        OrderRequest orderRequest = new OrderRequest(
                detailRequests,
                address,
                phone ,
                tvAppliedCode.getText().toString()
        );
        Log.d("OrderRequest", new Gson().toJson(orderRequest));

        orderApi.createOrder(orderRequest)
                .enqueue(new Callback<OrderResponse>() {
                    @Override
                    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            String orderId = response.body().getOrderId();
                            double amount = response.body().getTotalPrice();

                            TransactionRequest transactionReq = new TransactionRequest(orderId, "Paypal", amount);
                            orderApi.createTransaction(transactionReq)
                                    .enqueue(new Callback<TransactionResponse>() {
                                        @Override
                                        public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> transactionRes) {
                                            if (transactionRes.isSuccessful() && transactionRes.body() != null) {
                                                String transactionId = transactionRes.body().getTransactionId();

                                                CreatePaypalOrderRequest req = new CreatePaypalOrderRequest(transactionId,orderId);
                                                orderApi.createPaypalOrder(req)
                                                        .enqueue(new Callback<PaypalOrderResponse>() {
                                                            @Override
                                                            public void onResponse(Call<PaypalOrderResponse> call, Response<PaypalOrderResponse> paypalRes) {
                                                                if (paypalRes.isSuccessful() && paypalRes.body() != null) {
                                                                    String paypalOrderId = paypalRes.body().getId();
                                                                    openPaypalBrowser(paypalOrderId);
                                                                } else {
                                                                    Log.e("PayPal", "Tạo Paypal Order thất bại: " + paypalRes.code());
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<PaypalOrderResponse> call, Throwable t) {
                                                                Log.e("PayPal", "Lỗi tạo Paypal Order: " + t.getMessage());
                                                            }
                                                        });
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<TransactionResponse> call, Throwable t) {
                                            Log.e("PayPal", "Lỗi tạo transaction: " + t.getMessage());
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderResponse> call, Throwable t) {
                        Log.e("PayPal", "Lỗi tạo order: " + t.getMessage());
                    }
                });
    }

    public void onPaypalReturn(String paypalOrderId) {
        orderApi.capturePaypalOrder(new CapturePaypalOrderRequest(paypalOrderId))
                .enqueue(new Callback<CaptureResponse>() {
                    @Override
                    public void onResponse(Call<CaptureResponse> call, Response<CaptureResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            String referenceId = response.body().getPurchaseUnits().get(0).getReferenceId();
                            String orderId = referenceId.substring(4); // Xoá "REF-"
                            String transactionId = referenceId;

                            orderApi.completeTransaction(transactionId)
                                    .enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.isSuccessful()) {
                                                orderApi.completeOrder(orderId)
                                                        .enqueue(new Callback<ResponseBody>() {
                                                            @Override
                                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                if (response.isSuccessful()) {
                                                                    Toast.makeText(getContext(), "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                                Log.e("PayPal", "Lỗi completeOrder: " + t.getMessage());
                                                            }
                                                        });
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Log.e("PayPal", "Lỗi completeTransaction: " + t.getMessage());
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onFailure(Call<CaptureResponse> call, Throwable t) {
                        Log.e("PayPal", "Lỗi capture: " + t.getMessage());
                    }
                });
    }

    private void openPaypalBrowser(String paypalOrderId) {
        String url = paypalBaseUrl + paypalOrderId;
        CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
        intent.launchUrl(requireContext(), Uri.parse(url));
    }
    private float parseSize(String sizeStr) {
        try {
            return Float.parseFloat(sizeStr);
        } catch (NumberFormatException e) {
            Log.e("ParseSize", "Lỗi parse size từ chuỗi: " + sizeStr + ", dùng mặc định 3");
            return 3.0f;
        }
    }
}
