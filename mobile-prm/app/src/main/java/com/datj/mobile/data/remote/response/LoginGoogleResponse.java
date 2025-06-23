package com.datj.mobile.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class LoginGoogleResponse {
    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }
}
