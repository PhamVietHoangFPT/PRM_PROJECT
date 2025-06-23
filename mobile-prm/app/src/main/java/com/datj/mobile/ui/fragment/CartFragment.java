package com.datj.mobile.ui.fragment;

import android.content.Context;
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

import com.datj.mobile.R;
import com.datj.mobile.data.local.CartManager;
import com.datj.mobile.data.remote.model.CartItem;
import com.datj.mobile.ui.main.MainActivity;

import java.util.List;

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
            Fragment checkoutFragment = new CheckoutFragment();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_view, checkoutFragment);
            transaction.addToBackStack(null); // để quay lại được
            transaction.commit();
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
