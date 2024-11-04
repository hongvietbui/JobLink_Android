package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.domain.dao.IMessageDAO;
import com.SE1730.Group3.JobLink.src.domain.dao.IMessageDAO_Impl;
import com.SE1730.Group3.JobLink.src.domain.entities.Message;
import com.SE1730.Group3.JobLink.src.presentation.adapters.MessageAdapter;
import com.SE1730.Group3.JobLink.src.domain.utilities.signalR.ChatHubService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChatActivity extends BaseActivity {

    private List<Message> messageList;
    private MessageAdapter messageAdapter;
    private RecyclerView recyclerViewMessages;
    private EditText editTextMessage;
    private ImageView buttonSend, buttonBack;
    private UUID senderId;
    private UUID receiverId;
    private UUID jobId;


    @Inject
    IMessageDAO messageDAO;

    private BroadcastReceiver messageReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        bindingView();
        bindingAction();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String senderIdString = sharedPreferences.getString("userId", null);

        // Get the senderId, receiverId, and jobId from the Intent

        String receiverIdString = getIntent().getStringExtra("receiverId");
        String jobIdString = getIntent().getStringExtra("jobId");

        if (senderIdString != null) {
            senderId = UUID.fromString(senderIdString);
        }
        if (receiverIdString != null) {
            receiverId = UUID.fromString(receiverIdString);
        }
        if (jobIdString != null) {
            jobId = UUID.fromString(jobIdString);
        }

        // Start ChatHubService
        Intent serviceIntent = new Intent(this, ChatHubService.class);
        startService(serviceIntent); // This ensures the service is running

        loadMessages();

        // Set up the message adapter
        messageAdapter = new MessageAdapter(messageList, senderId);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(messageAdapter);

        // Register BroadcastReceiver to listen for new messages
        registerMessageReceiver();
    }

    private void bindingView() {
        recyclerViewMessages = findViewById(R.id.recyclerViewChat);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.imageViewSend);
        buttonBack = findViewById(R.id.imageViewBack);
    }

    private void bindingAction() {
        buttonSend.setOnClickListener(this::OnBtnSendClick);
        buttonBack.setOnClickListener(this::OnBtnBackClick);
        editTextMessage.setOnClickListener(this::OnEdtTextMessageClick);

    }

    private void OnEdtTextMessageClick(View view) {
        checkKeyboard();
    }

    private void OnBtnSendClick(View view) {
        String messageText = editTextMessage.getText().toString().trim();
        if (!messageText.isEmpty()) {
            sendMessage(messageText);
            editTextMessage.setText("");
        }
    }

    private void OnBtnBackClick(View view) {
        onBackPressed();
    }


    private void sendMessage(String text) {
        Message message = new Message(null, senderId, receiverId, text, jobId, true);
        ChatHubService.sendMessage(message);
    }

    private void registerMessageReceiver() {
        messageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get new message from Intent   and add to Adapter
                if ("NewMessageReceived".equals(intent.getAction())) {
                    Message newMessage = (Message) intent.getSerializableExtra("message");
                    if (newMessage != null) {
                        messageAdapter.addMessage(newMessage);
                        recyclerViewMessages.scrollToPosition(messageAdapter.getItemCount() - 1); // Scroll to bottom
                    }
                }
            }
        };

        IntentFilter filter = new IntentFilter("NewMessageReceived");
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, filter);
    }

    private void loadMessages() {
        // Use Executor to perform database access on a background thread
        Executors.newSingleThreadExecutor().execute(() -> {
            messageList = messageDAO.getAllMessageBetweenTwoUser(senderId, receiverId);
            runOnUiThread(() -> {
                messageAdapter.updateMessages(messageList); // Update the adapter with the loaded messages
                recyclerViewMessages.scrollToPosition(messageAdapter.getItemCount() - 1); // Scroll to bottom
            });
        });
    }

    private void checkKeyboard(){
        final View chatActivity = findViewById(R.id.chatActivity);

        chatActivity.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();

                chatActivity.getWindowVisibleDisplayFrame(rect);

                int heightDiff = chatActivity.getRootView().getHeight() - rect.height();

                if(heightDiff > 0.25 * chatActivity.getRootView().getHeight()){
                    //keyboard display
                    if(messageList.size() > 0){
                        recyclerViewMessages.scrollToPosition(messageList.size()-1);
                        chatActivity.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (messageReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
        }
    }
}

