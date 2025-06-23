package com.datj.mobile.util;

import android.content.Context;
import android.content.SharedPreferences;

public class CartManager {
    private static final String PREF_NAME = "CartPrefs";
    private static final String KEY_CART_COUNT = "cartCount";

    // Lấy số lượng mặt hàng từ SharedPreferences
    public static int getCartCount(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_CART_COUNT, 0); // Mặc định là 0 nếu chưa có
    }

    // Cập nhật số lượng mặt hàng
    public static void setCartCount(Context context, int count) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_CART_COUNT, count);
        editor.apply();
    }
}