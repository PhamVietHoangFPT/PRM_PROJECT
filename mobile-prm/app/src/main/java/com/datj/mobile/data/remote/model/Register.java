package com.datj.mobile.data.remote.model;

import com.google.gson.annotations.SerializedName;

public class Register {
    @SerializedName("name")
    private String name;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("address")
    private String address;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("birthday")
    private String birthday; // Dùng String để gửi định dạng ISO như "2002-04-12T14:48:21"

    @SerializedName("gender")
    private String gender; // "Male", "Female", "Other"

    // Constructor
    public Register(String name, String phoneNumber, String address, String email, String password, String birthday, String gender) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.gender = gender;
    }

    // Getters và Setters (tùy chọn, nếu cần)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getBirthday() { return birthday; }
    public void setBirthday(String birthday) { this.birthday = birthday; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
}