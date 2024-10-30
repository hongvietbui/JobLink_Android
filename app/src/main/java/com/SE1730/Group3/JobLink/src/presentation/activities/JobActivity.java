package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);
        jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);
        bindingViews();
        setEvents();
        observeCreateJobResult();
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
    }

    private void setEvents() {
        edtDate.setOnClickListener(v -> openDatePickerDialog());
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

    private void calculateTotalPrice() {
        try {
            double jobPrice = Double.parseDouble(edtJobPrice.getText().toString().trim());
            double totalPrice = jobPrice * 1.1; // 10% VAT
            DecimalFormat df = new DecimalFormat("#,###.##");
            tvTotalPrice.setText("Tổng cộng (bao gồm VAT): " + df.format(totalPrice) + " VND");
        } catch (NumberFormatException e) {
            tvTotalPrice.setText("Giá trị không hợp lệ");
        }
    }

    private void createJob() {
        if (!checkFormValidation()) {
            return;
        }

        String name = edtJobName.getText().toString().trim();
        String description = edtJobDescription.getText().toString().trim();
        String date = edtDate.getText().toString().trim();
        String time = edtTime.getText().toString().trim();

        // Đảm bảo định dạng "dd/MM/yyyy" và "HH:mm" có đủ cả giờ và phút
        if (!time.contains(":")) {
            time += ":00"; // Thêm phút mặc định nếu không có
        }

        try {
            LocalDateTime startDate = LocalDateTime.parse(date + "T" + time, DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm"));
            int duration = getDurationFromRadioGroup();

            double jobPrice = Double.parseDouble(edtJobPrice.getText().toString().trim());
            Log.d("CreateJobRequest", "Name: " + name + ", Description: " + description + ", Duration: " + duration + ", Price: " + jobPrice + ", StartTime: " + startDate.toString()+ ", EndTime: " + startDate.plusHours(duration).toString());

            jobViewModel.createJob(name, description, duration, jobPrice, "", startDate.toString(), startDate.plusHours(duration).toString());
        } catch (Exception e) {
            Snackbar.make(findViewById(android.R.id.content), "Định dạng ngày/giờ không hợp lệ", Snackbar.LENGTH_SHORT).show();
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
                Snackbar.make(findViewById(android.R.id.content), result.getMessage(), Snackbar.LENGTH_SHORT).show();
                if (result.getStatus() == 200) { // Giả sử 200 là thành công
                    finish();
                }
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Failed to create job", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean checkFormValidation() {
        return !Validator.areFieldsNullOrEmpty(edtJobName, edtJobDescription, edtJobPrice, edtDate, edtTime);
    }
}
