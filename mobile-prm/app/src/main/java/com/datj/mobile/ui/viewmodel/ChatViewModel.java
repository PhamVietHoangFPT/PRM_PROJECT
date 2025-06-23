package com.datj.mobile.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.datj.mobile.data.remote.model.Message;
import com.datj.mobile.data.repository.ChatRepository;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;

public class ChatViewModel extends ViewModel {
    private final ChatRepository chatRepository = new ChatRepository();
    private final MutableLiveData<List<Message>> messages = new MutableLiveData<>();
    private ListenerRegistration listener;

    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    public void startListening(String chatId) {
        listener = chatRepository.listenForMessages(chatId, (value, error) -> {
            if (error == null && value != null) {
                List<Message> messageList = new ArrayList<>();
                for (DocumentSnapshot doc : value.getDocuments()) {
                    messageList.add(doc.toObject(Message.class));
                }
                messages.postValue(messageList);
            }
        });
    }

    public void sendMessage(String chatId, Message message) {
        chatRepository.sendMessage(chatId, message, task -> {
            if (!task.isSuccessful()) {
                System.err.println("Lỗi gửi tin nhắn: " + task.getException());
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (listener != null) {
            listener.remove();
        }
    }
}