package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.data.models.all.TopUpDTO;
import com.SE1730.Group3.JobLink.src.presentation.adapters.TopUpAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TopUpHistoryActivity extends BaseActivity {
    private EditText edtFromDate, edtToDate;
    private RecyclerView recyclerViewTopUpHistory;
    private List<TopUpDTO> topUpList;
    private TopUpAdapter topUpHistoryAdapter;
    private Button btnFilter;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    private void bindingViews() {
        edtFromDate = findViewById(R.id.edtFromDate);
        edtToDate = findViewById(R.id.edtToDate);
        btnFilter = findViewById(R.id.btnFilter);
        recyclerViewTopUpHistory = findViewById(R.id.recyclerTopUpHistory);
    }

    private void bindingAction(){
        btnFilter.setOnClickListener(this::onBtnFilterClick);
    }

    private boolean isValid(String date) {
        dateFormat.setLenient(false);
        try{
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void onBtnFilterClick(View view) {
        String fromDate = edtFromDate.getText().toString();
        String toDate = edtToDate.getText().toString();

        if(!isValid(fromDate) || !isValid(toDate)) {
            edtFromDate.setError("Invalid date, please enter a valid date");
            return;
        }
        Toast.makeText(this, "Filtering top up history", Toast.LENGTH_SHORT).show();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_history);
        bindingViews();
        bindingAction();
        topUpList = new ArrayList<>();
        topUpHistoryAdapter = new TopUpAdapter(topUpList);
        recyclerViewTopUpHistory.setAdapter(topUpHistoryAdapter);
    }
}