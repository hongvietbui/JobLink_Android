package com.SE1730.Group3.JobLink.src.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.data.models.all.NotificationDTO;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<NotificationDTO> notifications;

    public NotificationAdapter(List<NotificationDTO> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationDTO notification = notifications.get(position);
        holder.messageTextView.setText(notification.getMessage());
        holder.timestampTextView.setText(notification.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView, timestampTextView;

        NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.contentTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
        }
    }
}
