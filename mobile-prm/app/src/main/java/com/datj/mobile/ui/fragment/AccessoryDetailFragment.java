package com.datj.mobile.ui.fragment;

import static com.datj.mobile.data.remote.RetrofitClient.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.datj.mobile.R;
import com.datj.mobile.data.local.CartStorage;
import com.datj.mobile.data.remote.api.AccessoryApiService;
import com.datj.mobile.data.remote.model.Accessory;
import com.datj.mobile.data.remote.model.CartItem;
import com.datj.mobile.data.local.CartManager;
import com.datj.mobile.ui.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccessoryDetailFragment extends Fragment {

    private Accessory accessory;
    private int accessoryId;
    private Call<Accessory> accessoryCall;

    public AccessoryDetailFragment() {}

    public static AccessoryDetailFragment newInstance(int accessoryId) {
        AccessoryDetailFragment fragment = new AccessoryDetailFragment();
        Bundle args = new Bundle();
        args.putInt("accessoryId", accessoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accessory_detail, container, false);

        int accessoryId = getArguments().getInt("accessoryId");
        AccessoryApiService apiService = getAccessoryApiService();

        accessoryCall = apiService.getAccessoryById(accessoryId);
        accessoryCall.enqueue(new Callback<Accessory>() {
            @Override
            public void onResponse(Call<Accessory> call, Response<Accessory> response) {
                if (response.isSuccessful() && response.body() != null) {
                    accessory = response.body();
                    setupUI(view); // cập nhật giao diện
                } else {
                    Toast.makeText(getContext(), "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Accessory> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    private void setupUI(View view) {
        ViewPager2 imagePager = view.findViewById(R.id.imagePager);
        imagePager.setAdapter(new AccessoryImageAdapter(accessory.getAccessoryImages()));
        double price = accessory.getPrice(); // giả sử giá kiểu double
        String priceFormatted = String.format("%.2f", price);

        ((TextView) view.findViewById(R.id.detailName)).setText(accessory.getName());
        ((TextView) view.findViewById(R.id.detailKarat)).setText("Karat: " + accessory.getKarat());
        ((TextView) view.findViewById(R.id.detailWeight)).setText("Gold weight: " + accessory.getMaterialWeight() + "g");
        ((TextView) view.findViewById(R.id.detailQuantity)).setText("Available: " + accessory.getQuantity());
        ((TextView) view.findViewById(R.id.detailType)).setText("Type: " + accessory.getAccessoryType().getName());
        ((TextView) view.findViewById(R.id.detailWagePrice)).setText("Wage Price: " + accessory.getAccessoryType().getProcessingPrice() + "$");
        ((TextView) view.findViewById(R.id.detailPrice)).setText("Price: " + priceFormatted + "$");
        ((TextView) view.findViewById(R.id.detailShape)).setText("Shape: " + accessory.getShape().getName());

        Button buttonAddToCart = view.findViewById(R.id.addToCartButton);
        Spinner spinnerSize = view.findViewById(R.id.sizeSpinner);
        buttonAddToCart.setOnClickListener(v -> {
            String selectedSize = spinnerSize.getSelectedItem().toString();
            CartManager.addItem(new CartItem(accessory, selectedSize, 1));
            CartStorage.saveCart(getContext(), CartManager.getItems());
            Toast.makeText(getContext(), "Add to cart successfully", Toast.LENGTH_SHORT).show();
            ((MainActivity) requireActivity()).updateCartBadge();
        });
        ImageView backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

    }
    @Override
    public void onDestroyView() {
        if (accessoryCall != null && !accessoryCall.isCanceled()) {
            accessoryCall.cancel();
        }
        super.onDestroyView();
    }
}