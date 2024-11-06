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
    private UUID currentUserId;

    public MessageAdapter(List<Message> messages, UUID currentUserId) {
        this.messages = messages;
        this.currentUserId = currentUserId;
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getSenderId().toString().equals(currentUserId.toString())) {
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
        if(message == null)
            return;

        if (holder instanceof SentMessageViewHolder) {
            ((SentMessageViewHolder) holder).textMessageSent.setText(message.getMessage());
        } else if (holder instanceof ReceivedMessageViewHolder) {
            ((ReceivedMessageViewHolder) holder).textMessageReceived.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        if(messages != null)
            return messages.size();
        return  0;
    }

    // Thêm tin nhắn mới vào danh sách và cập nhật RecyclerView
    public void addMessage(Message message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1); // Thông báo cập nhật vị trí cuối cùng
    }

    public void updateMessages(List<Message> newMessages) {
        this.messages = newMessages;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder {
        TextView textMessageSent;

        SentMessageViewHolder(View itemView) {
            super(itemView);
            textMessageSent = itemView.findViewById(R.id.textMessageSent);
        }
    }

    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        TextView textMessageReceived;

        ReceivedMessageViewHolder(View itemView) {
            super(itemView);
            textMessageReceived = itemView.findViewById(R.id.textMessageReceived);
        }
    }
}

