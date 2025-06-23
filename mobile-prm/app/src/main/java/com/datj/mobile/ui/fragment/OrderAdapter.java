package com.datj.mobile.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datj.mobile.R;
import com.datj.mobile.data.remote.model.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
       // holder.orderIdTextView.setText("Đơn hàng #" + order.getOrderId());
       // holder.statusTextView.setText(getStatusText(order.getStatus()));
       // holder.createdAtTextView.setText("Ngày tạo: " + order.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return orderList != null ? orderList.size() : 0;
    }

    // Chuyển trạng thái sang tiếng Việt
    private String getStatusText(String status) {
        switch (status) {
            case "Pending":
                return "Chờ xử lý";
            case "Processing":
                return "Đang xử lý";
            case "Confirmed":
                return "Đã xác nhận";
            case "Delivering":
                return "Đang giao";
            case "Completed":
                return "Hoàn thành";
            case "Failed":
                return "Thất bại";
            default:
                return status;
        }
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTextView, statusTextView, createdAtTextView;

        OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            createdAtTextView = itemView.findViewById(R.id.createdAtTextView);
        }
    }
}