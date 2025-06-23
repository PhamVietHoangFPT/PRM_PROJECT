package com.datj.mobile.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datj.mobile.R;
import com.datj.mobile.data.remote.model.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {
    private List<Message> messageList;
    private String currentUserId;

    public ChatAdapter(List<Message> messageList, String currentUserId) {
        this.messageList = messageList;
        this.currentUserId = currentUserId;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_chat, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.messageText.setText(message.getContent());
        if (message.getSenderId().equals(currentUserId)) {
            holder.messageText.setGravity(android.view.Gravity.END);
            holder.messageText.setBackgroundResource(R.drawable.message_background_user);
        } else {
            holder.messageText.setGravity(android.view.Gravity.START);
            holder.messageText.setBackgroundResource(R.drawable.message_background);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        MessageViewHolder(View itemView) {
            super(itemView);
           // messageText = itemView.findViewById(R.id.messageText);
        }
    }
}