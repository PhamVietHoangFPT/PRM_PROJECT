package com.datj.mobile.ui.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.datj.mobile.R;
import com.datj.mobile.data.remote.model.AccessoryImage;

import java.util.List;

public class AccessoryImageAdapter extends RecyclerView.Adapter<AccessoryImageAdapter.ImageViewHolder> {

    private List<AccessoryImage> imageList;

    public AccessoryImageAdapter(List<AccessoryImage> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new ImageViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(imageList.get(position).getUrl())
                .error(R.drawable.ic_profile_placeholder)
                .into((ImageView) holder.itemView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}