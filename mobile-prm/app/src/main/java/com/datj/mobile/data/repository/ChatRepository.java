package com.datj.mobile.data.repository;

import com.datj.mobile.data.remote.model.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ChatRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference chatCollection = db.collection("chats");

    // Gửi tin nhắn
    public void sendMessage(String chatId, Message message, OnCompleteListener<Void> callback) {
        chatCollection.document(chatId)
                .collection("messages")
                .add(message);

    }

    // Lắng nghe tin nhắn mới
    public ListenerRegistration listenForMessages(String chatId, EventListener<QuerySnapshot> listener) {
        return chatCollection.document(chatId)
                .collection("messages")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener(listener);
    }
}