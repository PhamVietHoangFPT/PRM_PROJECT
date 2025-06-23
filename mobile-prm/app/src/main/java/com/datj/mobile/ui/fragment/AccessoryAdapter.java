package com.datj.mobile.ui.fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.datj.mobile.R;
import com.datj.mobile.data.remote.model.Accessory;
import com.datj.mobile.data.remote.model.AccessoryImage;

import java.util.List;

public class AccessoryAdapter extends RecyclerView.Adapter<AccessoryAdapter.AccessoryViewHolder> {
    private List<Accessory> accessoryList;
    private Context context;
    private OnAccessoryClickListener listener;

    public AccessoryAdapter(List<Accessory> accessoryList, Context context) {
        this.accessoryList = accessoryList;
        this.context = context;
    }

    public AccessoryAdapter(List<Accessory> accessories, OnAccessoryClickListener listener) {
        this.accessoryList = accessories;
        this.listener = listener;
    }

    public interface OnAccessoryClickListener {
        void onAccessoryClick(Accessory accessory);
    }

    public AccessoryAdapter(List<Accessory> accessoryList) {
        this.accessoryList = accessoryList;
    }

    @NonNull
    @Override
    public AccessoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_accessory, parent, false);

        return new AccessoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccessoryViewHolder holder, int position) {
        Accessory accessory = accessoryList.get(position);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onAccessoryClick(accessory);
        });

        String name = accessory.getName();
        Log.d("AccessoryAdapter", "Đang vẽ: " + name);
        holder.nameTextView.setText(name);
        holder.accessoryNameTextView.setText(accessory.getName());
        holder.accessoryTypeTextView.setText("Type: " + accessory.getAccessoryType().getName());
        holder.accessoryKaratTextView.setText(accessory.getKarat() + " Karat");
        holder.itemView.setOnClickListener(v -> {
            int id = accessory.getAccessoryId();
            Fragment detailFragment = AccessoryDetailFragment.newInstance(id);
            FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, detailFragment)
                    .addToBackStack(null)
                    .commit();
        });
        List<AccessoryImage> images = accessory.getAccessoryImages();
        if (images != null && !images.isEmpty()) {
            String imageUrl = images.get(0).getUrl();
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.imageView);

        }

    }

    @Override
    public int getItemCount() {
        Log.d("AccessoryAdapter", "Số item cần hiển thị: " + (accessoryList != null ? accessoryList.size() : 0));
        return accessoryList != null ? accessoryList.size() : 0;
    }

    static class AccessoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView accessoryNameTextView = itemView.findViewById(R.id.accessoryNameTextView);
        TextView accessoryTypeTextView = itemView.findViewById(R.id.accessoryTypeTextView);
        TextView accessoryKaratTextView = itemView.findViewById(R.id.accessoryKaratTextView);

        public AccessoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.accessoryImageView);
            nameTextView = itemView.findViewById(R.id.accessoryNameTextView);
        }
    }

    public void setAccessoryList(List<Accessory> newList) {
        this.accessoryList = newList;
        notifyDataSetChanged();
    }
}