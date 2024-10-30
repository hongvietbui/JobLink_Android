package com.SE1730.Group3.JobLink.src.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.SE1730.Group3.JobLink.R;
import com.squareup.picasso.Picasso;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class JobImageFragment extends Fragment {
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_image, container, false);
        imageView = view.findViewById(R.id.imageView);

        // Get data from the activity or ViewModel
        if (getArguments() != null) {
            String avatarUrl = getArguments().getString("avatar");
            Picasso.get()
                    .load(avatarUrl)
                    .error(R.drawable.ic_image_error) // Placeholder or error image
                    .into(imageView);
        }

        return view;
    }
}
