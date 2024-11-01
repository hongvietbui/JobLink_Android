package com.SE1730.Group3.JobLink.src.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.SE1730.Group3.JobLink.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class JobDetailsFragment extends Fragment {
    private TextView tvJobName, tvJobDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_details, container, false);
        tvJobName = view.findViewById(R.id.tvJobName);
        tvJobDescription = view.findViewById(R.id.tvJobDescription);

        if (getArguments() != null) {
            tvJobName.setText(getArguments().getString("jobName"));
            tvJobDescription.setText(getArguments().getString("description"));
        }

        return view;
    }
}
