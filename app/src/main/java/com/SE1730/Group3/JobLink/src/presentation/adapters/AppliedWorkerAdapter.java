package com.SE1730.Group3.JobLink.src.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.data.models.all.UserDTO;
import com.SE1730.Group3.JobLink.src.domain.useCases.AcceptWorkerUseCase;
import com.SE1730.Group3.JobLink.src.domain.useCases.RejectWorkerUseCase;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.ViewAppliedWorkerViewModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AppliedWorkerAdapter extends RecyclerView.Adapter<AppliedWorkerAdapter.ViewHolder> {
    private List<UserDTO> appliedWorkers;
    private OnWorkerClickListener listener;
    private UUID jobId;
    private ViewAppliedWorkerViewModel viewModel;

    // Constructor mới với ViewModel
    public AppliedWorkerAdapter(List<UserDTO> appliedWorkers, OnWorkerClickListener listener,
                                UUID jobId, ViewAppliedWorkerViewModel viewModel) {
        this.appliedWorkers = appliedWorkers;
        this.listener = listener;
        this.jobId = jobId;
        this.viewModel = viewModel;
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

        // Gọi ViewModel để xử lý khi chấp nhận hoặc từ chối
        holder.btnApprove.setOnClickListener(v -> {
            try {
                viewModel.acceptWorker(jobId, worker.getId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Toast.makeText(holder.itemView.getContext(), "Worker Accepted", Toast.LENGTH_SHORT).show();
        });

        holder.btnDecline.setOnClickListener(v -> {
            try {
                viewModel.rejectWorker(jobId, worker.getId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Toast.makeText(holder.itemView.getContext(), "Worker Rejected", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return appliedWorkers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFullName, textViewEmail, textViewPhone, textViewDob, textViewAddress;
        ImageView imageViewAvatar;
        Button btnApprove, btnDecline;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFullName = itemView.findViewById(R.id.textViewFullName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
            textViewDob = itemView.findViewById(R.id.textViewDob);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar);
            btnApprove = itemView.findViewById(R.id.buttonAccept);
            btnDecline = itemView.findViewById(R.id.buttonDecline);
        }
    }

    public interface OnWorkerClickListener {
        void onWorkerClick(UserDTO worker);
    }
}

