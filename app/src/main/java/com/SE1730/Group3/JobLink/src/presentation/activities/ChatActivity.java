package com.SE1730.Group3.JobLink.src.presentation.activities;

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

public class ChatActivity extends BaseActivity {

    private List<Message> messageList = new ArrayList<>();
    private MessageAdapter messageAdapter;
    private RecyclerView recyclerViewMessages;
    private EditText editTextMessage;
    private ImageView buttonSend, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        bindingView();
        bindingAction();
    }

    private void bindingView(){
        recyclerViewMessages = findViewById(R.id.recyclerViewChat);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.imageViewSend);
        buttonBack = findViewById(R.id.imageViewBack);

        messageAdapter = new MessageAdapter(messageList);
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
