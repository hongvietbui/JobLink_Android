package com.SE1730.Group3.JobLink.src.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.data.models.all.JobWorkerDTO;
import com.SE1730.Group3.JobLink.src.data.models.all.UserDTO;
import com.SE1730.Group3.JobLink.src.domain.entities.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AppliedWorkerAdapter extends RecyclerView.Adapter<AppliedWorkerAdapter.ViewHolder> {
    private List<UserDTO> appliedWorkers;
    private OnWorkerClickListener listener;

    public AppliedWorkerAdapter(List<UserDTO> appliedWorkers, OnWorkerClickListener listener) {
        this.appliedWorkers = appliedWorkers;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_applied_worker, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserDTO worker = appliedWorkers.get(position);
        holder.textViewFullName.setText(worker.getFirstName() + " " + worker.getLastName());
        holder.textViewEmail.setText(worker.getEmail());
        holder.textViewPhone.setText(worker.getPhoneNumber());
        holder.textViewDob.setText(worker.getDateOfBirth());
        holder.textViewAddress.setText(worker.getAddress());

        // Tải ảnh đại diện sử dụng Picasso
        Picasso.get()
                .load(worker.getAvatar())
                .into(holder.imageViewAvatar);

        // Xử lý click item
        holder.itemView.setOnClickListener(v -> listener.onWorkerClick(worker));
    }

    @Override
    public int getItemCount() {
        return appliedWorkers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFullName, textViewEmail, textViewPhone, textViewDob, textViewAddress;
        ImageView imageViewAvatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFullName = itemView.findViewById(R.id.textViewFullName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
            textViewDob = itemView.findViewById(R.id.textViewDob);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar);
        }
    }

    public interface OnWorkerClickListener {
        void onWorkerClick(UserDTO worker);
    }
}

