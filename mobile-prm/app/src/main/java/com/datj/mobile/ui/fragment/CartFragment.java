package com.datj.mobile.ui.fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;

import com.datj.mobile.R;
import com.datj.mobile.data.local.CartManager;
import com.datj.mobile.data.remote.model.CartItem;
import com.datj.mobile.ui.main.MainActivity;

import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment {
    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private TextView totalPriceText;
    private Button checkoutButton;

    public CartFragment() {}

    private OnCartChangedListener cartChangedListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.cartRecyclerView);
        totalPriceText = view.findViewById(R.id.totalPriceText);
        checkoutButton = view.findViewById(R.id.checkoutButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<CartItem> cartItems = CartManager.getItems();
        adapter = new CartAdapter(requireContext(), cartItems, () -> {
            adapter.notifyDataSetChanged();
            double total = CartManager.calculateTotal();
            totalPriceText.setText("Total: $" + String.format("%.2f", total));
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).onCartChanged();
            }
        });
        recyclerView.setAdapter(adapter);

        refreshCart();

        checkoutButton.setOnClickListener(v -> {
            double total = CartManager.calculateTotal(); // Tổng tiền USD

            if (Double.isNaN(total) || total <= 0) {
                Toast.makeText(getContext(), "Tổng tiền không hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Log để kiểm tra rõ
            Log.d("CHECKOUT", "Tổng tiền USD: " + total);
            Toast.makeText(getContext(), String.format(Locale.US, "Đang mở VNPAY với %.2f USD", total), Toast.LENGTH_SHORT).show();

            // Giới hạn tối đa USD (tương đương < 200 triệu VND với tỷ giá hiện tại 24,700)
            if (total > 8000) { // 8000 USD ≈ 197 triệu VND
                Toast.makeText(getContext(), "Giá trị quá lớn! Vui lòng giảm xuống dưới 8000 USD.", Toast.LENGTH_LONG).show();
                return;
            }

            // ✅ Gửi sang helper
            com.datj.mobile.util.VnpayHelper.startPaymentWithIpFetch(requireContext(), total);



        });



        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCartChangedListener) {
            cartChangedListener = (OnCartChangedListener) context;
        }
    }

    private void refreshCart() {
        adapter.notifyDataSetChanged();
        double total = CartManager.calculateTotal();
        totalPriceText.setText("Total: $" + String.format("%.2f", total));
    }
    public interface OnCartChangedListener {
        void onCartChanged();
    }
}
