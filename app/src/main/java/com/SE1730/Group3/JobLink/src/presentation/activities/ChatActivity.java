package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.domain.entities.Message;
import com.SE1730.Group3.JobLink.src.presentation.adapters.MessageAdapter;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatActivity extends BaseActivity {

    private List<Message> messageList = new ArrayList<>();
    private MessageAdapter messageAdapter;
    private RecyclerView recyclerViewMessages;
    private EditText editTextMessage;
    private ImageView buttonSend, buttonBack;
    private UUID userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        bindingView();
        bindingAction();
    }

    private UUID getUserIdFromSharedPreferences() {
        //Todo: sua thanh get tu trang truoc
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userIdString = sharedPreferences.getString("userId", null);

        if (userIdString != null) {
            return UUID.fromString(userIdString);  // Chuyển đổi chuỗi về UUID
        } else {
            return null;  // Xử lý trường hợp userId không tồn tại
        }
    }


    private void bindingView(){
        userId = getUserIdFromSharedPreferences();
        recyclerViewMessages = findViewById(R.id.recyclerViewChat);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.imageViewSend);
        buttonBack = findViewById(R.id.imageViewBack);

        messageAdapter = new MessageAdapter(messageList, userId);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(messageAdapter);
    }

    private void bindingAction(){
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = editTextMessage.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    sendMessage(messageText);
                    editTextMessage.setText("");
                }
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void sendMessage(String text) {
//        Message message = new Message(text, true);
//        messageList.add(message);
//        messageAdapter.notifyItemInserted(messageList.size() - 1);
//        recyclerViewMessages.scrollToPosition(messageList.size() - 1);
    }
}
