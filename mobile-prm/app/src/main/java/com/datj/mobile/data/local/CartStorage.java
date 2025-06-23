package com.datj.mobile.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.datj.mobile.data.remote.model.CartItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartStorage {
    private static final String PREF_NAME = "CartPrefs";
    private static final String KEY_CART = "cartItems";

    public static void saveCart(Context context, List<CartItem> cartItems) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartItems);
        editor.putString(KEY_CART, json);
        editor.apply();
    }

    public static List<CartItem> loadCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_CART, null);
        if (json == null) return new ArrayList<>();
        Type type = new TypeToken<List<CartItem>>() {}.getType();
        return new Gson().fromJson(json, type);
    }
}