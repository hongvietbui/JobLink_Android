package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.databinding.ActivityJobDetailsBinding;
import com.SE1730.Group3.JobLink.src.data.models.response.JobOwnerDetailsResp;
import com.SE1730.Group3.JobLink.src.domain.enums.JobStatus;
import com.SE1730.Group3.JobLink.src.domain.useCases.AssignJobUseCase;
import com.SE1730.Group3.JobLink.src.domain.useCases.GetJobByIdUseCase;
import com.SE1730.Group3.JobLink.src.domain.useCases.GetUserRoleOfJobUserCase;
import com.SE1730.Group3.JobLink.src.domain.useCases.JobDetailUsecase;
import com.SE1730.Group3.JobLink.src.presentation.adapters.ViewPagerAdapter;
import com.SE1730.Group3.JobLink.src.presentation.fragments.JobDetailsFragment;
import com.SE1730.Group3.JobLink.src.presentation.fragments.JobImageFragment;
import com.SE1730.Group3.JobLink.src.presentation.fragments.MapFragment;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class JobDetailsActivity extends BaseActivity {
    @Inject
    GetUserRoleOfJobUserCase getUserRoleOfJobUserCase;

    @Inject
    JobDetailUsecase jobDetailUsecase;

    @Inject
    AssignJobUseCase assignJobUseCase;

    @Inject
    GetJobByIdUseCase getJobByIdUseCase;

    CompositeDisposable compositeDisposable;

    private ViewPager2 viewPager;
    private ImageButton btnLeft, btnRight;
    private Button btnMap, btnImage, btnDetails;
    private ImageView ivJobOwner;
    private TextView tvName, tvemail, tvLocation, tvphone;

    private Button btnAssign;
    private Button btnListApplicant;
    private Button btnCompleteJob;
    private Button btnChatWithOwner;

    private UUID jobId;
    private UUID receiverId;

    private ProgressBar progressBar;

    private ShimmerFrameLayout shimmerFrameLayout;

    private LinearLayout llContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        compositeDisposable = new CompositeDisposable();
        onBinding();
        setEvents();

        loadJobDetails();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void loadJobDetails() {
        try {
            shimmerFrameLayout.startShimmer();

            Intent intent = getIntent();
            String jobIdString = intent.getStringExtra("jobId");

            Disposable getUserRoleDisposable = getUserRoleOfJobUserCase.execute(UUID.fromString(jobIdString))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(apiResp -> {
                        if (apiResp.getStatus() == 200) {
                            String role = apiResp.getData();
                            Log.d("JobDetailsActivity", "User role: " + role);

                            Disposable getJobByIdDisposable = getJobByIdUseCase.execute(jobId)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(resp -> {
                                        if (resp.getData() != null) {
                                            JobStatus jobStatus = resp.getData().getStatus();
                                            //Check if user role is jobOwner or not
                                            if (role.equals("JobOwner")) {
                                                btnChatWithOwner.setVisibility(Button.GONE);
                                                btnAssign.setVisibility(Button.GONE);
                                                //Check if job is in progress
                                                if(jobStatus.equals(JobStatus.IN_PROGRESS)){
                                                    btnListApplicant.setVisibility(Button.GONE);
                                                    btnCompleteJob.setVisibility(Button.VISIBLE);
                                                }
                                                //Check if job is waiting for applicants
                                                else if(jobStatus.equals(JobStatus.WAITING_FOR_APPLICANTS) || jobStatus.equals(JobStatus.PENDING_APPROVAL)){
                                                    btnListApplicant.setVisibility(Button.VISIBLE);
                                                    btnCompleteJob.setVisibility(Button.GONE);
                                                }
                                            //Check if user role is worker or not
                                            } else if(role.equals("Worker")){
                                                btnCompleteJob.setVisibility(Button.GONE);
                                                btnListApplicant.setVisibility(Button.GONE);
                                                btnAssign.setVisibility(Button.GONE);
                                                btnChatWithOwner.setVisibility(Button.VISIBLE);
                                            //If worker not assigned
                                            } else {
                                                btnCompleteJob.setVisibility(Button.GONE);
                                                btnListApplicant.setVisibility(Button.GONE);
                                                btnAssign.setVisibility(Button.VISIBLE);
                                                btnChatWithOwner.setVisibility(Button.GONE);
                                            }

                                            shimmerFrameLayout.stopShimmer();
                                            shimmerFrameLayout.setVisibility(View.GONE);

                                            llContent.setVisibility(View.VISIBLE);
                                        } else {
                                            Log.e("JobDetailsActivity", "Job data is null");

                                            shimmerFrameLayout.stopShimmer();
                                            shimmerFrameLayout.setVisibility(View.GONE);

                                            llContent.setVisibility(View.VISIBLE);
                                        }
                                    }, throwable -> {
                                        // Error handling for getJobByIdUseCase
                                        Log.e("JobDetailsActivity", "Error fetching job details", throwable);

                                        shimmerFrameLayout.stopShimmer();
                                        shimmerFrameLayout.setVisibility(View.GONE);

                                        llContent.setVisibility(View.VISIBLE);
                                    });

                            compositeDisposable.add(getJobByIdDisposable);

                            // Add getJobByIdDisposable to CompositeDisposable if needed
                        } else {
                            Log.e("JobDetailsActivity", "Failed to get user role");
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);

                            llContent.setVisibility(View.VISIBLE);
                        }
                    }, throwable -> {
                        // Error handling for getUserRoleOfJobUserCase
                        Log.e("JobDetailsActivity", "Error fetching user role", throwable);
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);

                        llContent.setVisibility(View.VISIBLE);
                    });

            compositeDisposable.add(getUserRoleDisposable);

            if (jobIdString != null) {
                jobId = UUID.fromString(jobIdString);
                Log.d("JobDetailsActivity", "Job ID: " + jobId);
            } else {
                Log.e("JobDetailsActivity", "Job ID string is null");
                tvName.setText("Job ID not provided");
                return; // Exit early if jobIdString is null
            }

            var getJobByIdDisposable = getJobByIdUseCase.execute(jobId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(resp -> {
                        receiverId = resp.getData().getOwnerId();
                    }, throwable -> {
                        // Error handling for getJobByIdUseCase
                        Log.e("JobDetailsActivity", "Error fetching job details", throwable);
                    });

            compositeDisposable.add(getJobByIdDisposable);

            Disposable jobDetailDisposable = jobDetailUsecase.execute(jobId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(resp -> {
                        if (resp.getStatus() != 200) {
                            tvName.setText("Details not available");
                            tvemail.setText("No email provided");
                            tvphone.setText("No phone number available");
                        } else {
                            JobOwnerDetailsResp jobDetails = resp.getData();
                            Log.d("JobDetailsActivity", "Job Details: " + jobDetails);

                            // Update UI with job details
                            tvName.setText(jobDetails.getFirstName() + " " + jobDetails.getLastName());
                            tvemail.setText(jobDetails.getEmail());
                            tvphone.setText(jobDetails.getPhoneNumber());

                            // Prepare fragments with job details
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
                            if(jobDetails.getLat()!= null && jobDetails.getLon()!=null){
                                mapBundle.putDouble("lat", jobDetails.getLat());
                                mapBundle.putDouble("lon", jobDetails.getLon());
                            }else {
                                mapBundle.putDouble("lat", 21.05);
                                mapBundle.putDouble("lon", 105.58);
                            }
                            mapFragment.setArguments(mapBundle);

                            // Set up the ViewPager adapter with fragments
                            List<Fragment> fragments = List.of(jobDetailsFragment, jobImageFragment, mapFragment);
                            ViewPagerAdapter adapter = new ViewPagerAdapter(this, fragments);
                            viewPager.setAdapter(adapter);

                            updateButtonStyles(0);
                        }
                    }, throwable -> {
                        // Error handling for jobDetailUsecase
                        Log.e("JobDetailsActivity", "Error loading job details" + throwable.getStackTrace(), throwable);
                        tvName.setText("Error loading job details");
                    });

            compositeDisposable.add(jobDetailDisposable);

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

        btnAssign = findViewById(R.id.btnAssign);
        btnListApplicant = findViewById(R.id.btnListApplicant);
        btnCompleteJob = findViewById(R.id.btnCompleteJob);
        btnChatWithOwner = findViewById(R.id.btnChatWithOwner);

        progressBar = findViewById(R.id.progressBar);

        shimmerFrameLayout = findViewById(R.id.shimmer_loading);
        llContent = findViewById(R.id.llContent);
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

        btnListApplicant.setOnClickListener(v -> startAppliedWorkersActivity());

        btnAssign.setOnClickListener(v -> assignJob(jobId));

        btnChatWithOwner.setOnClickListener(v -> startChatActivity());
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

    private void startAppliedWorkersActivity() {
        Intent intent = new Intent(this, AppliedWorkersActivity.class);
        intent.putExtra("jobId", jobId.toString());
        startActivity(intent);
    }

    private void assignJob(UUID jobId) {
        try{
            btnAssign.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);

            Disposable assignJobDisposable = assignJobUseCase.execute(jobId.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(apiResp -> {
                        progressBar.setVisibility(View.GONE);
                        if (apiResp.getStatus() == 200) {
                            Toast.makeText(this, "Job assigned successfully", Toast.LENGTH_SHORT).show();
                            btnCompleteJob.setVisibility(Button.GONE);
                            btnListApplicant.setVisibility(Button.GONE);
                            btnAssign.setVisibility(Button.GONE);
                            btnChatWithOwner.setVisibility(Button.VISIBLE);
                        } else {
                            Toast.makeText(this, "You've assigned this job already", Toast.LENGTH_SHORT).show();
                        }
                    }, throwable -> {
                        progressBar.setVisibility(View.GONE);
                        // Error handling for assignJobUseCase
                        Log.e("JobDetailsActivity", "You've accepted this job already", throwable);
                        Toast.makeText(this, "You've assigned this job already", Toast.LENGTH_SHORT).show();
                    });

            compositeDisposable.add(assignJobDisposable);
        }catch (Exception ex){
            Toast.makeText(this, "You've accepted this job already", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    private void startChatActivity() {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("jobId", jobId.toString());
        intent.putExtra("receiverId", receiverId);
        startActivity(intent);
    }
}
