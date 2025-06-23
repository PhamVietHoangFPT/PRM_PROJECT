package com.datj.mobile.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.datj.mobile.data.remote.MyApplication;
import com.datj.mobile.data.remote.model.CartItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final String CART_KEY = "cartItems";
    private static final String PREF_NAME = "CartPrefs";
    private static final List<CartItem> cartItems = new ArrayList<>();

    public static void addItem(CartItem item) {
        cartItems.add(item);
    }

    public static List<CartItem> getItems() {
        return cartItems;
    }

    public static int getTotalQuantity() {
        int total = 0;
        for (CartItem item : cartItems) {
            total += item.getQuantity();
        }
        return total;
    }
    public static List<CartItem> getCartItems(Context context) {
        return CartStorage.loadCart(context);
    }
    public static void setItems(List<CartItem> items) {
        cartItems.clear();
        cartItems.addAll(items);
    }

    public static void saveCartItems(Context context, List<CartItem> items) {
        CartStorage.saveCart(context, items);
    }

    public static void clear() {
        cartItems.clear();
    }
    public static void removeItem(Context context, CartItem itemToRemove) {
        List<CartItem> current = getCartItems(context);
        if (current.removeIf(item -> item.getAccessory().getAccessoryId() == itemToRemove.getAccessory().getAccessoryId()
                && item.getSize().equals(itemToRemove.getSize()))) {
            saveCartItems(context, current);
        }
    }
    public static double calculateTotal() {
        double total = 0;
        for (CartItem item : getItems()) {
            total += item.getAccessory().getAccessoryType().getProcessingPrice();
            total += item.getAccessory().getPrice();
        }
        return total;
    }

}