package com.datj.mobile.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }
}
