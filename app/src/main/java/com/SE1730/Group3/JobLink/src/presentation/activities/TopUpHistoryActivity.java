package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.domain.dao.IUserDAO;
import com.SE1730.Group3.JobLink.src.presentation.adapters.TopUpAdapter;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.TopupHistoryViewModel;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TopUpHistoryActivity extends BaseActivity {
    private EditText edtFromDate, edtToDate;
    private RecyclerView recyclerViewTopUpHistory;
    private List<TopUpDTO> topUpList;
    private TopUpAdapter topUpHistoryAdapter;
    private Button btnFilter;
    private EditText fromDate, toDate;
    private TopupHistoryViewModel topupHistoryViewModel;
    private RecyclerView topUpRecyclerView;
    private TopUpAdapter adapter;

    @Inject
    TopupUsecase topupUsecase;
    IUserDAO userDAO;

    private void bindingView() {
        btnFilter = findViewById(R.id.btnFilter);
        fromDate = findViewById(R.id.edtFromDate);
        toDate = findViewById(R.id.edtToDate);
        topUpRecyclerView = findViewById(R.id.recyclerTopUpHistory);
    }

    private void bindingAction(){
        btnFilter.setOnClickListener(this::onBtnFilterClick);
    }

    private void onBtnFilterClick(View view) {
        UUID userId = getUserIdFromSharedPreferences();

        if(userId == null){
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd",Locale.getDefault());
        try{
            Date fromDateValue = dateFormat.parse(fromDate.getText().toString());
            Date toDateValue = dateFormat.parse(toDate.getText().toString());

            if(fromDateValue != null && toDateValue != null){
                fetchTopUpHistory(fromDateValue, toDateValue);
            }else{
                Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            Toast.makeText(this, "Date format is yyyy-mm-dd", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "Filtering top up history", Toast.LENGTH_SHORT).show();
    }

    private void fetchTopUpHistory(Date fromDate, Date toDate) {
        try{
            topupHistoryViewModel.TopUpHistory(fromDate, toDate);
        } catch (IOException e) {
            Toast.makeText(this, "Cant get user transaction history!!", Toast.LENGTH_SHORT).show();
        }
    }

    //Get userId
    private UUID getUserIdFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userIdString = sharedPreferences.getString("userId", null);
        if (userIdString != null) {
            Log.d("TopUpHistoryActivity", "User ID found: " + userIdString);
            return UUID.fromString(userIdString);
        } else {
            Log.d("TopUpHistoryActivity", "User ID not found in SharedPreferences.");
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_history);
        bindingView();
        topupHistoryViewModel = new ViewModelProvider(this).get(TopupHistoryViewModel.class);
        bindingAction();
        setUpRecyclerView();
        observeViewModel();

    }

    private void observeViewModel() {
        topupHistoryViewModel.topUpResult.observe(this, apiResp -> {
            if (apiResp != null && apiResp.getData() != null) {
                adapter.setData(apiResp.getData());
            } else {
                Toast.makeText(this, "Lỗi: " + (apiResp != null ? apiResp.getMessage() : "Lỗi không xác định"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpRecyclerView() {
        topUpRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TopUpAdapter();
        topUpRecyclerView.setAdapter(adapter);
    }


}
