package com.datj.mobile.ui.customview;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.datj.mobile.R;
import com.datj.mobile.util.CartDatabaseHelper;
import com.datj.mobile.util.CartManager;

import me.leolin.shortcutbadger.ShortcutBadger;

public class CartActivity extends AppCompatActivity {
    private CartDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_cart);

        dbHelper = new CartDatabaseHelper(this);

        // Giả sử thêm sản phẩm khi nhấn nút
       // findViewById(R.id.add_to_cart_button).setOnClickListener(v -> {
          //  int productId = 1; // Ví dụ: ID sản phẩm từ danh sách sản phẩm
          //  int quantity = 1;  // Số lượng thêm vào
          //  dbHelper.addToCart(productId, quantity); // Thêm vào SQLite
          //  int cartCount = dbHelper.getCartCount(); // Lấy tổng số lượng
          //  updateCartBadge(cartCount);              // Cập nhật badge
      // });
    }

    private void updateCartBadge(int cartCount) {
        ShortcutBadger.applyCount(this, cartCount); // Hiển thị badge
    }
}