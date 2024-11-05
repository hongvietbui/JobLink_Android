package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.domain.utilities.Validator;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.JobViewModel;
import com.google.android.material.snackbar.Snackbar;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.DecimalFormat;
import java.util.Calendar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class JobActivity extends BaseActivity {

    private JobViewModel jobViewModel;
    private EditText edtJobName, edtJobDescription, edtJobPrice, edtDate, edtTime;
    private RadioGroup rgDuration;
    private Button btnCreateJob;
    private TextView tvTotalPrice;
    private ImageButton btn_Back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);
        jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);

        bindingViews();
        setEvents();
        setupBackAction();
        observeCreateJobResult();
    }

    private void setupBackAction() {
        btn_Back.setOnClickListener(v -> finish());
    }

    private void bindingViews() {
        edtJobName = findViewById(R.id.edtJobName);
        edtJobDescription = findViewById(R.id.edtJobDescription);
        edtJobPrice = findViewById(R.id.edtJobPrice);
        edtDate = findViewById(R.id.dateEditText);
        edtTime = findViewById(R.id.timeEditText);
        rgDuration = findViewById(R.id.rgDuration);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnCreateJob = findViewById(R.id.btnCreateJob);
        btn_Back = findViewById(R.id.btnBack);
    }

    private void setEvents() {
        edtDate.setOnClickListener(v -> openDatePickerDialog());
        edtTime.setOnClickListener(v -> openTimePickerDialog());

        edtJobPrice.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) calculateTotalPrice();
        });

        btnCreateJob.setOnClickListener(v -> createJob());
    }

    private void openDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String date = String.format("%02d/%02d/%d", dayOfMonth, month1 + 1, year1);
            edtDate.setText(date);
        }, year, month, day);

        datePickerDialog.show();
    }

    private void openTimePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, selectedHour, selectedMinute) -> {
            String time = String.format("%02d:%02d", selectedHour, selectedMinute);
            edtTime.setText(time);
        }, hour, minute, true);

        timePickerDialog.show();
    }

    private void calculateTotalPrice() {
        try {
            double jobPrice = Double.parseDouble(edtJobPrice.getText().toString().trim());
            double totalPrice = jobPrice * 1.1; // 10% VAT
            DecimalFormat df = new DecimalFormat("#,###.##");
            tvTotalPrice.setText("Total (including VAT): " + df.format(totalPrice) + " VND");
        } catch (NumberFormatException e) {
            tvTotalPrice.setText("Invalid value");
        }
    }

    private void createJob() {
        if (!checkFormValidation()) {
            Snackbar.make(findViewById(android.R.id.content), "Please fill in all required information", Snackbar.LENGTH_SHORT).show();
            return;
        }

        String name = edtJobName.getText().toString().trim();
        String description = edtJobDescription.getText().toString().trim();
        String date = edtDate.getText().toString().trim();
        String time = edtTime.getText().toString().trim();

        // Ensure "dd/MM/yyyy" and "HH:mm" format with hours and minutes
        if (!time.contains(":")) {
            time += ":00"; // Add default minutes if not provided
        }

        try {
            LocalDateTime startDate = LocalDateTime.parse(date + "T" + time, DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm"));
            int duration = getDurationFromRadioGroup();
            double jobPrice = Double.parseDouble(edtJobPrice.getText().toString().trim());

            Log.d("CreateJobRequest", "Name: " + name + ", Description: " + description + ", Duration: " + duration + ", Price: " + jobPrice + ", StartTime: " + startDate + ", EndTime: " + startDate.plusHours(duration));

            jobViewModel.createJob(name, description, duration, jobPrice, "", startDate.toString(), startDate.plusHours(duration).toString());
        } catch (Exception e) {
            Snackbar.make(findViewById(android.R.id.content), "Invalid date/time format", Snackbar.LENGTH_SHORT).show();
        }
    }

    private int getDurationFromRadioGroup() {
        int checkedId = rgDuration.getCheckedRadioButtonId();
        if (checkedId == R.id.rbTwoHours) {
            return 2;
        } else if (checkedId == R.id.rbThreeHours) {
            return 3;
        } else {
            return 4;
        }
    }

    private void observeCreateJobResult() {
        jobViewModel.getCreateJobResult().observe(this, result -> {
            if (result != null) {
                Integer statusCode = result.getStatus();
                String message = result.getMessage();

                if (statusCode != null && statusCode == 201) { // Job creation success
                    Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
                    Intent intent = new Intent(JobActivity.this, ViewJobsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else if (statusCode != null && statusCode == 402) { // Handle payment required event
                    showPaymentRequiredDialog(message);
                } else {
                    Snackbar.make(findViewById(android.R.id.content), message != null ? message : "Unknown error", Snackbar.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Failed to create job", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean checkFormValidation() {
        return !Validator.areFieldsNullOrEmpty(edtJobName, edtJobDescription, edtJobPrice, edtDate, edtTime);
    }

    private void showPaymentRequiredDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Payment Required")
                .setMessage(message)
                .setPositiveButton("Recharge", (dialog, which) -> {
                    // Open recharge screen or page
                    Intent intent = new Intent(JobActivity.this, TransferActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
