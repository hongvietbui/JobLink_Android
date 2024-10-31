package com.SE1730.Group3.JobLink.src.presentation.adapters;

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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class AppliedWorkerAdapter extends RecyclerView.Adapter<AppliedWorkerAdapter.ViewHolder> {
    private List<UserDTO> appliedWorkers;
    private OnWorkerClickListener listener;
    private String jobId;
    private String accessToken;

    @Inject
    AcceptWorkerUseCase acceptWorkerUseCase;
    @Inject
    RejectWorkerUseCase rejectWorkerUseCase;

    public AppliedWorkerAdapter(List<UserDTO> appliedWorkers, OnWorkerClickListener listener,
                                String jobId, String accessToken) {
        this.appliedWorkers = appliedWorkers;
        this.listener = listener;
        this.jobId = jobId;
        this.accessToken = accessToken;
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

        holder.btnApprove.setOnClickListener(v -> {
            try {
                onBtnApproveClick(holder, worker);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        holder.btnDecline.setOnClickListener(v -> {
            try {
                onBtnDeclineClick(holder, worker);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void onBtnApproveClick(ViewHolder holder, UserDTO worker) throws IOException {
        // Xử lý chấp nhận công nhân và truyền jobId và accessToken
        acceptWorkerUseCase.execute(accessToken, jobId, worker.getId().toString()) // Cập nhật hàm execute để nhận thêm tham số
                .subscribeOn(Schedulers.io())
                .subscribe(resp -> {
                    Toast.makeText(holder.itemView.getContext(), "Worker Accepted", Toast.LENGTH_SHORT).show();
                }, error -> {
                    Toast.makeText(holder.itemView.getContext(), "Error Accepting Worker", Toast.LENGTH_SHORT).show();
                });
    }

    private void onBtnDeclineClick(ViewHolder holder, UserDTO worker) throws IOException {
        // Xử lý từ chối công nhân và truyền jobId và accessToken
        rejectWorkerUseCase.execute(accessToken, jobId, worker.getId().toString()) // Cập nhật hàm execute để nhận thêm tham số
                .subscribeOn(Schedulers.io())
                .subscribe(resp -> {
                    Toast.makeText(holder.itemView.getContext(), "Worker Rejected", Toast.LENGTH_SHORT).show();
                }, error -> {
                    Toast.makeText(holder.itemView.getContext(), "Error Rejecting Worker", Toast.LENGTH_SHORT).show();
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

