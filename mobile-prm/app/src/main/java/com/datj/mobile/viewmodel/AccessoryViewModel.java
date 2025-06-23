package com.datj.mobile.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.datj.mobile.data.remote.RetrofitClient;
import com.datj.mobile.data.remote.api.AccessoryApiService;
import com.datj.mobile.data.remote.model.Accessory;
import com.datj.mobile.data.remote.response.AccessoryResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccessoryViewModel extends ViewModel {
    private final MutableLiveData<List<Accessory>> accessories = new MutableLiveData<>();

    public LiveData<List<Accessory>> getAccessories() {
        return accessories;
    }

    public void fetchAccessories() {
        int pageSize = 50;
        int pageNumber = 3;
        AccessoryApiService service = RetrofitClient.getAccessoryApiService();
        service.getAccessories(pageSize,pageNumber).enqueue(new Callback<AccessoryResponse>() {
            @Override
            public void onResponse(Call<AccessoryResponse> call, Response<AccessoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    accessories.setValue(response.body().getAccessories());
                } else {
                    accessories.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<AccessoryResponse> call, Throwable t) {
                accessories.setValue(new ArrayList<>());
            }
        });
    }
}