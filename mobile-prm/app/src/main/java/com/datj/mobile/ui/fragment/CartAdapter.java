package com.datj.mobile.ui.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.datj.mobile.R;
import com.datj.mobile.data.remote.model.CartItem;
import com.datj.mobile.data.local.CartManager;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> cartItems;
    private OnCartChangedListener listener;

    public interface OnCartChangedListener {
        void onCartUpdated();
    }

    public CartAdapter(Context context, List<CartItem> cartItems, OnCartChangedListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.nameText.setText(item.getAccessory().getName());
        holder.priceText.setText("$" + String.format("%.2f", item.getAccessory().getPrice()));
        holder.sizeText.setText("Size: " + item.getSize());

        Glide.with(context)
                .load(item.getAccessory().getFirstImageUrl())
                .into(holder.imageView);

        holder.removeButton.setOnClickListener(v -> {
            CartManager.removeItem(context, item);
            cartItems.remove(position);
            notifyItemRemoved(position);
            listener.onCartUpdated();

        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameText, priceText, sizeText;
        Button removeButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cartImage);
            nameText = itemView.findViewById(R.id.cartName);
            priceText = itemView.findViewById(R.id.cartPrice);
            sizeText = itemView.findViewById(R.id.cartSize);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }
}
