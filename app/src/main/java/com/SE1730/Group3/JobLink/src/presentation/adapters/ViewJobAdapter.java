package com.SE1730.Group3.JobLink.src.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.data.models.listjob.ListJobReqDTO;

import java.util.List;

public class ViewJobAdapter extends RecyclerView.Adapter<ViewJobAdapter.JobViewHolder> {

    private List<ListJobReqDTO> jobList;

    public ViewJobAdapter(List<ListJobReqDTO> jobList) {
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for each job
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_job, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        ListJobReqDTO job = jobList.get(position);

        holder.jobName.setText(job.getName());
        holder.jobDescription.setText(job.getDescription());
        holder.jobAddress.setText(job.getAddress());
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView jobName, jobDescription, jobAddress;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            jobName = itemView.findViewById(R.id.jobName);
            jobDescription = itemView.findViewById(R.id.jobDescription);
            jobAddress = itemView.findViewById(R.id.jobAddress);
        }
    }
}
