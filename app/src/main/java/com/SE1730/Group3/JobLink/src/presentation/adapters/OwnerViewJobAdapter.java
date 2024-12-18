package com.SE1730.Group3.JobLink.src.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.data.models.all.JobDTO;

import java.util.List;

public class OwnerViewJobAdapter extends RecyclerView.Adapter<OwnerViewJobAdapter.JobViewHolder> {

    private List<JobDTO> jobList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onViewDetailsClick(JobDTO job);
    }

    public OwnerViewJobAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setJobs(List<JobDTO> jobs) {
        this.jobList = jobs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item_user_job, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        JobDTO job = jobList.get(position);
        holder.bind(job, listener);
    }

    @Override
    public int getItemCount() {
        return jobList != null ? jobList.size() : 0;
    }

    class JobViewHolder extends RecyclerView.ViewHolder {
        private TextView jobName;
        private TextView jobAddress;
        private TextView jobStatus;
        private TextView jobDuration;
        private TextView jobPrice;
        private Button buttonViewDetail;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            jobName = itemView.findViewById(R.id.jobNameUser);
            jobAddress = itemView.findViewById(R.id.jobAddressUser);
            jobStatus = itemView.findViewById(R.id.jobStatusUser);
            jobDuration = itemView.findViewById(R.id.DurationJobUser);
            jobPrice = itemView.findViewById(R.id.PriceJobUser);
            buttonViewDetail = itemView.findViewById(R.id.DetailOwnerJob_btn);
        }

        public void bind(JobDTO job, OnItemClickListener listener) {
            jobName.setText(job.getName());
            jobAddress.setText("Address: " + job.getAddress());
            jobStatus.setText("Status: " + job.getStatus());
            jobDuration.setText("Duration: " + job.getDuration() + " hours");
            jobPrice.setText("Price: $" + job.getPrice());

            // Set up button click to trigger listener
            buttonViewDetail.setOnClickListener(v -> listener.onViewDetailsClick(job));
        }
    }
}
