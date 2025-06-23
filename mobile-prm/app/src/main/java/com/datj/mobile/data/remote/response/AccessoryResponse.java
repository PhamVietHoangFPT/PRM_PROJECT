package com.datj.mobile.data.remote.response;

import com.datj.mobile.data.remote.model.Accessory;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccessoryResponse {
    @SerializedName("accessories")
    private List<Accessory> accessories;
    public List<Accessory> getAccessories() { return accessories; }
}