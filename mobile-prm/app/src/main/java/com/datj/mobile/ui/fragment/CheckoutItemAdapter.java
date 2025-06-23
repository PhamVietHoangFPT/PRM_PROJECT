package com.datj.mobile.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.datj.mobile.R;
import com.datj.mobile.data.remote.model.Accessory;
import com.datj.mobile.data.remote.model.CartItem;

import java.util.List;

public class CheckoutItemAdapter extends RecyclerView.Adapter<CheckoutItemAdapter.ViewHolder> {

    private List<CartItem> cartItems;

    public CheckoutItemAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        Accessory accessory = item.getAccessory();

        holder.checkoutName.setText(accessory.getName());
        holder.checkoutSize.setText("Size: " + item.getSize());
        holder.checkoutQuantity.setText("Qty: " + item.getQuantity());
        holder.checkoutPrice.setText("Price: $" + String.format("%.2f", accessory.getPrice()));

        if (accessory.getAccessoryImages() != null && !accessory.getAccessoryImages().isEmpty()) {
            String imageUrl = accessory.getAccessoryImages().get(0).getUrl();
            Glide.with(holder.itemView.getContext()).load(imageUrl).into(holder.checkoutImage);
        }
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView checkoutImage;
        TextView checkoutName, checkoutSize, checkoutQuantity, checkoutPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkoutImage = itemView.findViewById(R.id.checkoutImage);
            checkoutName = itemView.findViewById(R.id.checkoutName);
            checkoutSize = itemView.findViewById(R.id.checkoutSize);
            checkoutQuantity = itemView.findViewById(R.id.checkoutQuantity);
            checkoutPrice = itemView.findViewById(R.id.checkoutPrice);
        }
    }
}
