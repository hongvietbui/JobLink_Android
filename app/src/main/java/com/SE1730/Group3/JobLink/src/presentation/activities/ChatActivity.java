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

import java.util.ArrayList;
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

        initializeData();
        bindingView();
        bindingAction();
        // Register BroadcastReceiver to listen for new messages
        registerMessageReceiver();
    }

    private void bindingView() {
        recyclerViewMessages = findViewById(R.id.recyclerViewChat);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.imageViewSend);
        buttonBack = findViewById(R.id.imageViewBack);

        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
    }

    private void bindingAction() {
        buttonSend.setOnClickListener(this::OnBtnSendClick);
        buttonBack.setOnClickListener(view -> onBackPressed());
        editTextMessage.setOnClickListener(this::OnEdtTextMessageClick);

    }

    private void initializeData(){
        receiverId = getIntent().getStringExtra("receiverId") != null
                ? UUID.fromString(getIntent().getStringExtra("receiverId"))
                : null;

        jobId = getIntent().getStringExtra("jobId") != null
                ? UUID.fromString(getIntent().getStringExtra("jobId"))
                : null;

        var userDisposable = userDAO.getCurrentUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        user -> {
                            senderId = user.getId();
                            if (senderId != null && receiverId != null) {
                                // Khởi tạo MessageAdapter khi cả senderId và receiverId đã sẵn sàng
                                messageAdapter = new MessageAdapter(new ArrayList<>(), senderId);
                                recyclerViewMessages.setAdapter(messageAdapter);

                                messageList = messageDAO.getAllMessageBetweenTwoUser(senderId, receiverId);
                                setupObservers();
                            } else {
                                Log.e("ChatActivity", "senderId hoặc receiverId chưa được khởi tạo");
                            }
                        },
                        throwable -> Log.e("ChatActivity", "Error retrieving user: " + throwable.getMessage(), throwable)
                );

        disposables.add(userDisposable);
    }

    private void setupObservers() {
        if(messageList!=null){
            messageList.observe(this, messages -> {
                if(messages != null && !messages.isEmpty()){
                    messageAdapter.updateMessages(messages);
                    recyclerViewMessages.scrollToPosition(messageAdapter.getItemCount() - 1);
                } else {
                    Log.d("ChatActivity", "Received an empty message list, skipping update");
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

    private void sendMessage(String text) {
        Message message = new Message(null, senderId, receiverId, text, jobId, true);
        chatHubService.sendMessage(message);
    }

    private void registerMessageReceiver() {
        messageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if ("NewMessageReceived".equals(intent.getAction())) {
                    try {
                        var jsonAdapter = moshi.adapter(Message.class);
                        Message receivedMessage = jsonAdapter.fromJson(intent.getStringExtra("message"));
                        if (receivedMessage != null && messageAdapter != null &&
                                receivedMessage.getReceiverId() != null &&
                                receivedMessage.getSenderId().equals(receiverId)) {
                            messageAdapter.addMessage(receivedMessage);
                            recyclerViewMessages.scrollToPosition(messageAdapter.getItemCount() - 1);
                        } else {
                            Log.d("ChatActivity", "Message received is not for this chat");
                        }
                    } catch (Exception e) {
                        Log.e("ChatActivity", "Error parsing message: " + e.getMessage(), e);
                    }
                }
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, new IntentFilter("NewMessageReceived"));
    }

    private void checkKeyboard() {
        final View chatActivity = findViewById(R.id.chatActivity);
        chatActivity.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                chatActivity.getWindowVisibleDisplayFrame(rect);
                int heightDiff = chatActivity.getRootView().getHeight() - rect.height();
                if (heightDiff > 0.25 * chatActivity.getRootView().getHeight()) {
                    if (!messageAdapter.isEmpty()) {
                        recyclerViewMessages.scrollToPosition(messageAdapter.getItemCount() - 1);
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
        disposables.clear();
    }
}

