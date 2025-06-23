package com.datj.mobile.data.remote;

import android.util.Log;

import com.auth0.android.jwt.JWT;
import com.datj.mobile.data.local.TokenManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final TokenManager tokenManager;

    public AuthInterceptor() {
        this.tokenManager = TokenManager.getInstance();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = tokenManager.getToken();
        if (token != null) {
            JWT jwt = new JWT(token);
            String accountId = jwt.getClaim("accountId").asString();
            Log.d("TokenDebug", "AccountId: " + accountId);
        }
        Request.Builder requestBuilder = chain.request().newBuilder();

        if (token != null) {
            requestBuilder.addHeader("Authorization", "Bearer " + token);
        }
        return chain.proceed(requestBuilder.build());
    }
}
