package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.data.models.all.TopUpDTO;
import com.SE1730.Group3.JobLink.src.domain.dao.IUserDAO;
import com.SE1730.Group3.JobLink.src.domain.utilities.signalR.NotificationService;
import com.SE1730.Group3.JobLink.src.presentation.adapters.TopUpAdapter;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.TopUpViewModel;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@RequiresApi(api = Build.VERSION_CODES.O)
@AndroidEntryPoint
public class TopUpHistoryActivity extends BaseBottomActivity {
    private EditText edtFromDate, edtToDate;
    private Button btnFilter;
    private EditText fromDate, toDate;
    private TopUpViewModel topupViewModel;
    private RecyclerView topUpRecyclerView;
    private TopUpAdapter adapter;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private final CompositeDisposable disposables = new CompositeDisposable();
    private NotificationService notificationService;
    private boolean isBound = false;
    private boolean isFilterApplied = false;
    private int pageIndex = 1;
    private final int pageSize = 10;
    private boolean isLoading = false;
    private boolean hasMorePages = true;

    private void bindingView() {
        btnFilter = findViewById(R.id.btnFilter);
        fromDate = findViewById(R.id.edtFromDate);
        toDate = findViewById(R.id.edtToDate);
        topUpRecyclerView = findViewById(R.id.recyclerTopUpHistory);
    }

    private void bindingAction() {
        btnFilter.setOnClickListener(this::onBtnFilterClick);
        fromDate.setOnClickListener(view -> openDatePickerDialog(fromDate));
        toDate.setOnClickListener(view -> openDatePickerDialog(toDate));
    }



    private void openDatePickerDialog(EditText dateField) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            dateField.setText(dateFormat.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void onBtnFilterClick(View view) {
        try {
            Date fromDateValue = dateFormat.parse(fromDate.getText().toString());
            Date toDateValue = dateFormat.parse(toDate.getText().toString());

            if (fromDateValue != null && toDateValue != null) {
                isFilterApplied = true;
                adapter.setData(new ArrayList<>());
                fetchTopUpHistory(fromDateValue, toDateValue);
            } else {
                Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException | IOException e) {
            Toast.makeText(this, "Date format is yyyy-MM-dd", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchTopUpHistory(Date fromDate, Date toDate) throws IOException {
        isLoading = true;
        topupViewModel.getTopUpHistory(
                fromDate != null ? new java.sql.Date(fromDate.getTime()) : null,
                toDate != null ? new java.sql.Date(toDate.getTime()) : null
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_topup_history);
        topupViewModel = new ViewModelProvider(this).get(TopUpViewModel.class);
        Intent serviceIntent = new Intent(this, NotificationService.class);
        bindingView();
        bindingAction();
        setUpRecyclerView();
        observeViewModel();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        toDate.setText(dateFormat.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        fromDate.setText(dateFormat.format(calendar.getTime()));
        try {
            isFilterApplied=false;
            fetchTopUpHistory(null, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void observeViewModel() {
        topupViewModel.topUpResult.observe(this, apiResp -> {
            isLoading = false;

            if (apiResp != null && apiResp.getData() != null) {
                List<TopUpDTO> topUpHistory = apiResp.getData();
                if(topUpHistory.isEmpty()){
                    findViewById(R.id.tvNoTransactions).setVisibility(View.VISIBLE);
                }else{
                    findViewById(R.id.tvNoTransactions).setVisibility(View.GONE);
                    if(adapter == null){
                        adapter = new TopUpAdapter(this, topUpHistory);
                        topUpRecyclerView.setAdapter(adapter);
                    }else{
                        adapter.setData(topUpHistory);
                    }
                }
            } else {
                String errorMessage = apiResp != null ? apiResp.getMessage() : "Lỗi không xác định";
                // Ghi log chi tiết lỗi
                Log.e("TopUpHistoryActivity", "Lỗi: " + errorMessage);
                Toast.makeText(this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpRecyclerView() {
        topUpRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TopUpAdapter();
        topUpRecyclerView.setAdapter(adapter);
        topUpRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && !isLoading && hasMorePages &&
                        layoutManager.findLastVisibleItemPosition() >= adapter.getItemCount() - 3) {
                    pageIndex++;
                    try {
                        fetchTopUpHistory(null, null);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}
