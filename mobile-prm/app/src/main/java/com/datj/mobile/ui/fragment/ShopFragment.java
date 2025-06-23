package com.datj.mobile.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.datj.mobile.data.remote.RetrofitClient;
import com.datj.mobile.data.remote.api.OrderApiService;
import com.datj.mobile.data.remote.model.Order;
import com.datj.mobile.databinding.FragmentShopBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopFragment extends Fragment {
    private FragmentShopBinding binding;
    private OrderApiService orderApiService;
    private OrderAdapter orderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentShopBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orderApiService = RetrofitClient.getOrderApiService();
        setupRecyclerView();
        fetchOrders();


    }

    private void setupRecyclerView() {
        binding.ordersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderAdapter = new OrderAdapter(new ArrayList<>());
        binding.ordersRecyclerView.setAdapter(orderAdapter);
    }

    private void fetchOrders() {
        orderApiService.getOrders().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orderAdapter = new OrderAdapter(response.body());
                    binding.ordersRecyclerView.setAdapter(orderAdapter);
                    Log.d("ShopDebug", "Orders fetched: " + response.body().size());
                } else {
                    Toast.makeText(getContext(), "Không tải được đơn hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ShopDebug", "Failure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
