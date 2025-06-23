package com.datj.mobile.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.auth0.android.jwt.JWT;
import com.datj.mobile.data.remote.MyApplication;

public class TokenManager {
    private static final String PREF_NAME = "auth_prefs";
    private static final String KEY_TOKEN = "auth_token";
    private static final String KEY_ROLE = "auth_role";
    private static TokenManager instance;
    private final SharedPreferences sharedPreferences;

    private TokenManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized TokenManager getInstance() {
        if (instance == null) {
            instance = new TokenManager(MyApplication.getAppContext());
        }
        return instance;
    }

    public void clearToken() {
        sharedPreferences.edit().remove(KEY_TOKEN).apply();
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }
    public void saveToken(String token) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply();

    }
    public void decodeAndSaveRole(String token) {
        try {
            saveToken(token);
            JWT jwt = new JWT(token);
            String role = jwt.getClaim("role").asString();
            if (role != null) {
                sharedPreferences.edit().putString(KEY_ROLE, role).apply();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
