package com.datj.mobile.ui.customview;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.datj.mobile.R;
import com.datj.mobile.data.network.ApiClient;
import com.datj.mobile.data.remote.api.CartApiService;
import com.datj.mobile.data.remote.model.CartCountResponse;
import com.datj.mobile.data.remote.model.CartItem;
import com.datj.mobile.util.CartDatabaseHelper;

import me.leolin.shortcutbadger.ShortcutBadger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillingActivity extends AppCompatActivity {
    private CartDatabaseHelper dbHelper;
    private CartApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_billing);

        dbHelper = new CartDatabaseHelper(this);
        apiService = ApiClient.getCartApiService();

      //  Button placeOrderButton = findViewById(R.id.place_order_button);
       // placeOrderButton.setOnClickListener(v -> {
          //  syncCartWithServer(); // Đồng bộ khi nhấn đặt hàng
       // });
    }

//    private void syncCartWithServer() {
//        Cursor cursor = dbHelper.getCartItems();
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast()) {
//                int productId = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"));
//                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
//
//                // Gửi từng mục trong giỏ hàng lên server
//                CartItem cartItem = new CartItem(productId, quantity);
//                Call<Void> call = apiService.addCartItem(cartItem);
//                call.enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(Call<Void> call, Response<Void> response) {
//                        if (response.isSuccessful()) {
//                            // Đồng bộ thành công
//                        } else {
//                            Toast.makeText(BillingActivity.this, "Lỗi đồng bộ", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Void> call, Throwable t) {
//                        Toast.makeText(BillingActivity.this, "Không kết nối được server", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                cursor.moveToNext();
//            }
//        }
//        cursor.close();
//
//        // Sau khi đồng bộ thành công, xóa giỏ hàng cục bộ và cập nhật badge
//        dbHelper.clearCart();
//        updateCartBadge(0);
//
//        // (Tùy chọn) Lấy lại số lượng từ server nếu cần
//        fetchCartCountFromApi();
//    }

    private void updateCartBadge(int cartCount) {
        ShortcutBadger.applyCount(this, cartCount);
    }

    private void fetchCartCountFromApi() {
        Call<CartCountResponse> call = apiService.getCartCount();
        call.enqueue(new Callback<CartCountResponse>() {
            @Override
            public void onResponse(Call<CartCountResponse> call, Response<CartCountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int serverCartCount = response.body().getCount();
                    updateCartBadge(serverCartCount);
                }
            }

            @Override
            public void onFailure(Call<CartCountResponse> call, Throwable t) {
                Toast.makeText(BillingActivity.this, "Lỗi lấy dữ liệu từ server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
