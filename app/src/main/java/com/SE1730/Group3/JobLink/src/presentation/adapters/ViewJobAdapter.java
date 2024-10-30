package com.SE1730.Group3.JobLink.src.presentation.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.data.models.all.JobDTO;
import com.SE1730.Group3.JobLink.src.presentation.activities.JobDetailsActivity;

import java.util.List;
import java.util.UUID;

public class ViewJobAdapter extends RecyclerView.Adapter<ViewJobAdapter.JobViewHolder> {

    private List<JobDTO> jobList;
    private Context context;

    public ViewJobAdapter(Context context, List<JobDTO> jobList) {
        this.jobList = jobList;
        this.context = context; // Properly assign the context here
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

        holder.jobAddress.setText("Address: " + job.getAddress());
        holder.jobStatus.setText("Status: " + job.getStatus());
        holder.jobDuration.setText("Duration: " + job.getDuration() + " hours");
        holder.jobPrice.setText("Price: " + job.getPrice() + "$");

        holder.detailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context != null) {
                    Intent intent = new Intent(context, JobDetailsActivity.class);
                    UUID jobId = job.getId(); // Get the job ID as UUID
                    Log.d("ViewJobAdapter", "Job ID: " + jobId);

                    // Convert UUID to String before putting it into the Intent
                    intent.putExtra("jobId", jobId.toString());

                    context.startActivity(intent);
                } else {
                    Log.e("ViewJobAdapter", "Context is null when trying to create an Intent");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView jobName, jobDescription, jobAddress;
        TextView jobStatus, jobDuration, jobPrice;
        Button detailbtn;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            jobName = itemView.findViewById(R.id.jobName);
            jobDescription = itemView.findViewById(R.id.jobDescription);
            jobAddress = itemView.findViewById(R.id.jobAddress);
            jobStatus = itemView.findViewById(R.id.jobStatus);
            jobDuration = itemView.findViewById(R.id.DurationJob);
            jobPrice = itemView.findViewById(R.id.PriceJob);
            detailbtn = itemView.findViewById(R.id.detailButton);
        }
    }
}
