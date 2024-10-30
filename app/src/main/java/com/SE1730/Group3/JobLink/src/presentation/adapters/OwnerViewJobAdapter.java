package com.SE1730.Group3.JobLink.src.presentation.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.data.models.all.JobDTO;

import java.util.List;

public class OwnerViewJobAdapter  extends RecyclerView.Adapter<OwnerViewJobAdapter.JobViewHolder>{

    private List<JobDTO> jobList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onViewApplicantsClick(JobDTO job);
    }

    public OwnerViewJobAdapter(OnItemClickListener listener) {
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
                .inflate(R.layout.view_item_apply_job, parent, false);
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

    static class JobViewHolder extends RecyclerView.ViewHolder {

        private TextView jobName;
        private Button buttonViewApplicants;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            jobName = itemView.findViewById(R.id.jobName);
            buttonViewApplicants = itemView.findViewById(R.id.buttonViewApplicants);
        }

        public void bind(JobDTO job, OnItemClickListener listener) {
            jobName.setText(job.getName()); // Adjust based on your JobDTO
            buttonViewApplicants.setOnClickListener(v -> listener.onViewApplicantsClick(job));
        }
    }
}
