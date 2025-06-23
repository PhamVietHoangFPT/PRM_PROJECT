package com.datj.mobile.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.datj.mobile.R;
import com.datj.mobile.data.local.CartManager;
import com.datj.mobile.data.local.CartStorage;
import com.datj.mobile.ui.fragment.CartFragment;
import com.datj.mobile.ui.fragment.BlogFragment;
import com.datj.mobile.ui.fragment.HomeFragment;
import com.datj.mobile.ui.fragment.ProfileFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);

        CartManager.setItems(CartStorage.loadCart(this));
        updateCartBadge();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_shop) {
              //  selectedFragment = new ShopFragment();
            } else if (itemId == R.id.nav_blog) {
                selectedFragment = new BlogFragment();
            } else if (itemId == R.id.nav_notifications) {
               // selectedFragment = new NotificationFragment();
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_view, selectedFragment)
                        .commit();
            }
            return true;
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, new HomeFragment())
                    .commit();
        }

        ImageView iconCart = findViewById(R.id.icon_cart);
        iconCart.setOnClickListener(v -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_view, new CartFragment())
                    .addToBackStack(null)
                    .commit();
        });

    }
    public void onCartChanged() {
        updateCartBadge();
    }
    public void updateCartBadge() {
        TextView badge = findViewById(R.id.cart_badge);
        int totalQuantity = CartManager.getTotalQuantity();
        if (totalQuantity > 0) {
            badge.setVisibility(View.VISIBLE);
            badge.setText(String.valueOf(totalQuantity));
        } else {
            badge.setVisibility(View.GONE);
        }
    }
}