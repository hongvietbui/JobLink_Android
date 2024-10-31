package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.SE1730.Group3.JobLink.R;
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
import com.SE1730.Group3.JobLink.src.presentation.viewModels.JobDetailViewModel;
import com.google.android.material.snackbar.Snackbar;

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
    private Button btnAccept;
    private Button btnListApplicant;
    private Button btndoneJob;
    private UUID jobId;

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

                                            if (role.equals("JobOwner") && jobStatus.equals(JobStatus.IN_PROGRESS)) {
                                                btnAccept.setVisibility(Button.GONE);
//                                                btnCancel.setVisibility(Button.GONE);
                                                btnListApplicant.setVisibility(Button.GONE);
                                                btndoneJob.setVisibility(Button.VISIBLE);
                                            } else if (role.equals("JobOwner") && jobStatus.equals(JobStatus.WAITING_FOR_APPLICANTS)) {
                                                btnAccept.setVisibility(Button.GONE);
//                                                btnCancel.setVisibility(Button.GONE);
                                                btnListApplicant.setVisibility(Button.VISIBLE);
                                                btndoneJob.setVisibility(Button.GONE);
                                            } else {
                                                btnAccept.setVisibility(Button.VISIBLE);
//                                                btnCancel.setVisibility(Button.VISIBLE);
                                                btnListApplicant.setVisibility(Button.GONE);
                                                btndoneJob.setVisibility(Button.GONE);
                                            }
                                        } else {
                                            Log.e("JobDetailsActivity", "Job data is null");
                                        }
                                    }, throwable -> {
                                        // Error handling for getJobByIdUseCase
                                        Log.e("JobDetailsActivity", "Error fetching job details", throwable);
                                    });

                            compositeDisposable.add(getJobByIdDisposable);

                            // Add getJobByIdDisposable to CompositeDisposable if needed
                        } else {
                            Log.e("JobDetailsActivity", "Failed to get user role");
                        }
                    }, throwable -> {
                        // Error handling for getUserRoleOfJobUserCase
                        Log.e("JobDetailsActivity", "Error fetching user role", throwable);
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
        btnAccept = findViewById(R.id.btnAccept);
//        btnCancel = findViewById(R.id.btnCancel);
        btnListApplicant = findViewById(R.id.btnListApplicant);
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

        btnAccept.setOnClickListener(v -> assignJob(jobId));
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
            Disposable assignJobDisposable = assignJobUseCase.execute(jobId.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(apiResp -> {
                        if (apiResp.getStatus() == 200) {
                            Toast.makeText(this, "Job assigned successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "You've assigned this job already", Toast.LENGTH_SHORT).show();
                        }
                    }, throwable -> {
                        // Error handling for assignJobUseCase
                        Log.e("JobDetailsActivity", "You've accepted this job already", throwable);
                        Toast.makeText(this, "You've assigned this job already", Toast.LENGTH_SHORT).show();
                    });

            compositeDisposable.add(assignJobDisposable);
        }catch (Exception ex){
            Toast.makeText(this, "You've accepted this job already", Toast.LENGTH_SHORT).show();
        }
    }

    private void cancelJob(){
        Intent intent = new Intent(this, ViewJobActivity.class);
        startActivity(intent);
    }
}
