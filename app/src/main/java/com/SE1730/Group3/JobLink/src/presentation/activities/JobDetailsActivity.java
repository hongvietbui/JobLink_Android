package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.data.models.response.JobAndOwnerDetailsResponse;
import com.SE1730.Group3.JobLink.src.presentation.adapters.ViewPagerAdapter;
import com.SE1730.Group3.JobLink.src.presentation.fragments.JobDetailsFragment;
import com.SE1730.Group3.JobLink.src.presentation.fragments.JobImageFragment;
import com.SE1730.Group3.JobLink.src.presentation.fragments.MapFragment;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.JobDetailViewModel;

import java.util.List;
import java.util.UUID;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class JobDetailsActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private ImageButton btnLeft, btnRight;
    private Button btnMap, btnImage, btnDetails;
    private ImageView ivJobOwner;
    private TextView tvName, tvemail, tvLocation, tvphone;
    private Button btnAccept, btnCancel;
    private JobDetailViewModel jobDetailViewModel;
    private UUID jobId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        onBinding();
        setEvents();

        jobDetailViewModel = new ViewModelProvider(this).get(JobDetailViewModel.class);
        loadJobDetails();
    }

    private void loadJobDetails() {
        try {
            Intent intent = getIntent();
            String jobIdString = intent.getStringExtra("jobId");
            if (jobIdString != null) {
                jobId = UUID.fromString(jobIdString);
                Log.e("JobDetailsActivity", "Job ID: " + jobId);
            } else {
                Log.e("JobDetailsActivity", "Job ID string is null");
            }

            jobDetailViewModel.JobDetail(jobId);
            jobDetailViewModel.jobsDetailResult.observe(this, apiResp -> {
                if (apiResp.getStatus() == 200) {
                    JobAndOwnerDetailsResponse jobDetails = apiResp.getData();
                    Log.d("JobDetailsActivity", "Job Details: " + jobDetails.toString());

                    // Set text values
                    tvName.setText(jobDetails.getFirstName() + " " + jobDetails.getLastName());
                    tvemail.setText(jobDetails.getEmail());
                    tvphone.setText(jobDetails.getPhoneNumber());

                    // Create and set up fragments with arguments
                    JobDetailsFragment jobDetailsFragment = new JobDetailsFragment();
                    Bundle jobDetailsBundle = new Bundle();
                    jobDetailsBundle.putString("jobName", jobDetails.getJobName());
                    jobDetailsBundle.putString("description", jobDetails.getDescription());
                    jobDetailsFragment.setArguments(jobDetailsBundle);

                    JobImageFragment jobImageFragment = new JobImageFragment();
                    Bundle jobImageBundle = new Bundle();
                    jobImageBundle.putString("avatar", jobDetails.getAvatar());
                    jobImageFragment.setArguments(jobImageBundle);

                    MapFragment mapFragment = new MapFragment();
                    Bundle mapBundle = new Bundle();
                    mapBundle.putDouble("lat", jobDetails.getLat());
                    mapBundle.putDouble("lon", jobDetails.getLon());
                    mapFragment.setArguments(mapBundle);

                    List<Fragment> fragments = List.of(jobDetailsFragment, jobImageFragment, mapFragment);
                    ViewPagerAdapter adapter = new ViewPagerAdapter(this, fragments);
                    viewPager.setAdapter(adapter);

                    updateButtonStyles(0);
                } else {
                    tvName.setText("Details not available");
                    tvemail.setText("No email provided");
                    tvphone.setText("No phone number available");
                }
            });
        } catch (Exception e) {
            Log.e("JobDetailsActivity", "Error loading job details", e);
            tvName.setText("Error loading job details");
        }
    }

    private void onBinding() {
        viewPager = findViewById(R.id.viewPager);
        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);
        btnMap = findViewById(R.id.btnMap);
        btnImage = findViewById(R.id.btnImage);
        btnDetails = findViewById(R.id.btnDetails);
        ivJobOwner = findViewById(R.id.ivJobOwner);
        tvName = findViewById(R.id.tvName);
        tvphone = findViewById(R.id.tvphonenum);
        tvemail = findViewById(R.id.tvemail);
        btnAccept = findViewById(R.id.btnAccept);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void setEvents() {
        btnLeft.setOnClickListener(v -> viewPager.setCurrentItem(viewPager.getCurrentItem() - 1));
        btnRight.setOnClickListener(v -> viewPager.setCurrentItem(viewPager.getCurrentItem() + 1));

        btnDetails.setOnClickListener(v -> {
            viewPager.setCurrentItem(0, false);
            updateButtonStyles(0);
        });

        btnImage.setOnClickListener(v -> {
            viewPager.setCurrentItem(1, false);
            updateButtonStyles(1);
        });

        btnMap.setOnClickListener(v -> {
            viewPager.setCurrentItem(2, false);
            updateButtonStyles(2);
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateButtonStyles(position);
            }
        });
    }

    private void updateButtonStyles(int selectedButton) {
        // Reset button colors
        btnImage.setBackgroundColor(getResources().getColor(R.color.cyne10));
        btnMap.setBackgroundColor(getResources().getColor(R.color.green10));
        btnDetails.setBackgroundColor(getResources().getColor(R.color.aquamarine10));

        switch (selectedButton) {
            case 0:
                btnImage.setBackgroundColor(getResources().getColor(R.color.cyne15));
                break;
            case 1:
                btnMap.setBackgroundColor(getResources().getColor(R.color.green15));
                break;
            case 2:
                btnDetails.setBackgroundColor(getResources().getColor(R.color.aquamarine15));
                break;
        }
    }
}
