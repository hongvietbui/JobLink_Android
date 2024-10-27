package com.SE1730.Group3.JobLink.src.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.data.models.all.JobDTO;

import java.util.List;

public class ViewJobAdapter extends RecyclerView.Adapter<ViewJobAdapter.JobViewHolder> {

    private List<JobDTO> jobList;

    public ViewJobAdapter(List<JobDTO> jobList) {
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for each job
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_job, parent, false);
        return new JobViewHolder(view);
    }

    public void addJobs(List<JobDTO> newJobs) {
        int initialSize = jobList.size();
        jobList.addAll(newJobs);
        notifyItemRangeInserted(initialSize, newJobs.size());
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        JobDTO job = jobList.get(position);

        holder.jobName.setText(job.getName());
        holder.jobDescription.setText(job.getDescription());
        holder.jobAddress.setText(job.getAddress());

        // Set new fields
        holder.jobStatus.setText("Status: " + job.getStatus());
        holder.jobDuration.setText("Duration: " + job.getDuration() + " hours");
        holder.jobPrice.setText("Price: " + job.getPrice()+"$");
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView jobName, jobDescription, jobAddress;
        TextView jobStatus, jobDuration, jobPrice;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            jobName = itemView.findViewById(R.id.jobName);
            jobDescription = itemView.findViewById(R.id.jobDescription);
            jobAddress = itemView.findViewById(R.id.jobAddress);
            jobStatus = itemView.findViewById(R.id.jobStatus);
            jobDuration = itemView.findViewById(R.id.DurationJob);
            jobPrice = itemView.findViewById(R.id.PriceJob);
        }
    }
}
