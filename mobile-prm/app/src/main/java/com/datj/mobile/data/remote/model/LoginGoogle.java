package com.datj.mobile.data.remote.model;

import com.google.gson.annotations.SerializedName;

public class LoginGoogle {
    @SerializedName("email")
    private String email;

    public LoginGoogle(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
