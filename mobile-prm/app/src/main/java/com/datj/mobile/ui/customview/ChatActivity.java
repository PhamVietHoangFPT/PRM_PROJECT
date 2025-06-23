package com.datj.mobile.ui.customview;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datj.mobile.R;
import com.datj.mobile.data.remote.model.Message;
import com.datj.mobile.ui.viewmodel.ChatViewModel;
import com.datj.mobile.util.ChatAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView chatRecyclerView;
    private EditText messageEditText;
    private Button sendButton;
    private ChatAdapter chatAdapter;
    private List<Message> messageList;
    private DatabaseReference chatRef;
    private String currentUserId; // Khai báo biến ở đây
    private String storeRepId = "store_rep"; // ID của đại diện cửa hàng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Lấy currentUserId từ Firebase Authentication
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            currentUserId = user.getUid();
        } else {
            currentUserId = "guest"; // Giá trị mặc định nếu chưa đăng nhập
            Toast.makeText(this, "Vui lòng đăng nhập để chat", Toast.LENGTH_SHORT).show();
        }

        // Khởi tạo giao diện
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        messageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(messageList, currentUserId);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(chatAdapter);

        // Kết nối Firebase
        chatRef = FirebaseDatabase.getInstance().getReference("chats")
                .child(getChatRoomId(currentUserId, storeRepId));

        // Lắng nghe tin nhắn mới
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messageList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    if (message != null) {
                        messageList.add(message);
                    }
                }
                chatAdapter.notifyDataSetChanged();
                if (messageList.size() > 0) {
                    chatRecyclerView.scrollToPosition(messageList.size() - 1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ChatActivity.this, "Lỗi: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Gửi tin nhắn
        sendButton.setOnClickListener(v -> {
            String content = messageEditText.getText().toString().trim();
            if (!content.isEmpty()) {
                Message message = new Message(currentUserId, content, System.currentTimeMillis());
                chatRef.push().setValue(message);
                messageEditText.setText("");
            }
        });
    }

    private String getChatRoomId(String userId, String repId) {
        if (userId.compareTo(repId) < 0) {
            return userId + "_" + repId;
        } else {
            return repId + "_" + userId;
        }
    }
}
