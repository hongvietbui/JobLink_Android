package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.domain.dao.IMessageDAO;
import com.SE1730.Group3.JobLink.src.domain.dao.IUserDAO;
import com.SE1730.Group3.JobLink.src.domain.entities.Message;
import com.SE1730.Group3.JobLink.src.domain.utilities.signalR.ChatHubService;
import com.SE1730.Group3.JobLink.src.presentation.adapters.MessageAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class ChatActivity extends BaseBottomActivity {
    private MessageAdapter messageAdapter;
    private RecyclerView recyclerViewMessages;
    private LiveData<List<Message>> messageList;
    private EditText editTextMessage;
    private ImageView buttonSend, buttonBack;
    private UUID senderId;
    private UUID receiverId;
    private UUID jobId;

    private CompositeDisposable disposables = new CompositeDisposable();


    @Inject
    IMessageDAO messageDAO;

    @Inject
    IUserDAO userDAO;

    @Inject
    ChatHubService chatHubService;

    @Inject
    Moshi moshi;


    private BroadcastReceiver messageReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_chat);
        bindingView();
        bindingAction();

        var getCurrentUserDisposable = userDAO.getCurrentUser()
                .subscribeOn(Schedulers.io()) // Run the database query on an I/O thread
                .observeOn(AndroidSchedulers.mainThread()) // Observe the results on the main thread
                .subscribe(
                        u -> {
                            senderId = u.getId();

                            if (senderId != null && receiverId != null) {
                                messageList = messageDAO.getAllMessageBetweenTwoUser(senderId, receiverId);
                                setupObservers(); // Thiết lập observer sau khi messageList được khởi tạo
                            } else {
                                Log.e("ChatActivity", "senderId hoặc receiverId chưa được khởi tạo");
                            }
                        },
                        throwable -> {
                            // Handle the error here
                            Log.e("TAG", "Error retrieving user: " + throwable.getMessage());
                            throwable.printStackTrace();
                        }
                );

        disposables.add(getCurrentUserDisposable);
        // Get the senderId, receiverId, and jobId from the Intent
        String receiverIdString = getIntent().getStringExtra("receiverId");
        String jobIdString = getIntent().getStringExtra("jobId");

        if (receiverIdString != null) {
            receiverId = UUID.fromString(receiverIdString);
        }
        if (jobIdString != null) {
            jobId = UUID.fromString(jobIdString);
        }

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

    private void setupObservers() {
        if (messageList != null) {
            messageList.observe(this, messages -> {
                if (messages != null) {
                    messageAdapter = new MessageAdapter(messages, senderId);
                    recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
                    recyclerViewMessages.setAdapter(messageAdapter);

                    loadMessages();
                } else {
                    Log.e("ChatActivity", "No messages retrieved");
                }
            });
        } else {
            Log.e("ChatActivity", "messageList is null");
        }
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
        chatHubService.sendMessage(message);
    }

    private void registerMessageReceiver() {
        messageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get new message from Intent   and add to Adapter
                if ("NewMessageReceived".equals(intent.getAction())) {
                    var jsonAdapter = moshi.adapter(Message.class);
                    try{
                        Message receivedMessage = jsonAdapter.fromJson(intent.getStringExtra("message"));
                        if (receivedMessage.getReceiverId() != null && receivedMessage.getSenderId().equals(receiverId)) {
                            messageAdapter.addMessage(receivedMessage);
                            recyclerViewMessages.scrollToPosition(messageAdapter.getItemCount() - 1); // Scroll to bottom
                        }else{
                            Log.d("ChatActivity", "Message received is not for this chat: " + receivedMessage.getMessage());
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                        Log.d("ChatActivity", "Error parsing message: " + e.getMessage());
                    }
                }
            }
        };

        IntentFilter filter = new IntentFilter("NewMessageReceived");
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, filter);
    }

    private void loadMessages() {
        // Use Executor to perform database access on a background thread
        messageList.observe(this, messageList -> {
            runOnUiThread(() -> {
                messageAdapter.updateMessages(messageList); // Update the adapter with the loaded messages
                recyclerViewMessages.scrollToPosition(messageAdapter.getItemCount() - 1); // Scroll to bottom
            });
        });
    }

    private void checkKeyboard(){
        final View chatActivity = findViewById(R.id.chatActivity);

        var that = this;
        chatActivity.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();

                chatActivity.getWindowVisibleDisplayFrame(rect);

                int heightDiff = chatActivity.getRootView().getHeight() - rect.height();

                if(heightDiff > 0.25 * chatActivity.getRootView().getHeight()){
                    messageList.observe(that, messages -> {
                        if(!messages.isEmpty()){
                            recyclerViewMessages.scrollToPosition(messages.size()-1);
                            chatActivity.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    });
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

