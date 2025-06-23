package com.datj.mobile.util;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

public class FirebaseService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d("FCM", "Token: " + token);
        sendTokenToServer(token);
    }

    private void sendTokenToServer(String token) {
        // Gửi token lên backend của bạn (ví dụ: qua API)
    }
}