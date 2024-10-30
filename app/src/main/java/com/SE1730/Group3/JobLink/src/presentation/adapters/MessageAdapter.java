package com.SE1730.Group3.JobLink.src.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.domain.entities.Message;

import java.util.List;
import java.util.UUID;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> messages;
    private UUID currentUserId; // ID của người dùng hiện tại để phân biệt sent/received

    public MessageAdapter(List<Message> messages, UUID currentUserId) {
        this.messages = messages;
        this.currentUserId = currentUserId;
    }

    @Override
    public int getItemViewType(int position) {
        // So sánh senderId của tin nhắn với currentUserId để xác định kiểu tin nhắn
        if (messages.get(position).getSenderId().equals(currentUserId)) {
            return 1; // sent
        } else {
            return 2; // received
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        if (holder instanceof SentMessageViewHolder) {
            ((SentMessageViewHolder) holder).textMessageSent.setText(message.getText());
        } else if (holder instanceof ReceivedMessageViewHolder) {
            ((ReceivedMessageViewHolder) holder).textMessageReceived.setText(message.getText());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    // ViewHolder cho tin nhắn gửi đi
    static class SentMessageViewHolder extends RecyclerView.ViewHolder {
        TextView textMessageSent;

        SentMessageViewHolder(View itemView) {
            super(itemView);
            textMessageSent = itemView.findViewById(R.id.textMessageSent);
        }
    }

    // ViewHolder cho tin nhắn nhận về
    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        TextView textMessageReceived;

        ReceivedMessageViewHolder(View itemView) {
            super(itemView);
            textMessageReceived = itemView.findViewById(R.id.textMessageReceived);
        }
    }
}
