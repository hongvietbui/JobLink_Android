package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.domain.useCases.GetJobsUseCase;
import com.SE1730.Group3.JobLink.src.presentation.adapters.ViewJobAdapter;

import java.io.IOException;
import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class ViewJobsActivity extends BaseActivity {
    CompositeDisposable disposables = new CompositeDisposable();

    private RecyclerView recyclerViewJobs;
    private ViewJobAdapter viewJobAdapter;
    private Spinner spinnerSortBy;
    private Spinner spinnerSortOrder;
    private EditText editTextFilter;
    private Button buttonApplyFilter;
    private View progressBar;
    private ImageButton Add_btn;
    private int pageIndex = 1;
    private final int pageSize = 10;
    private boolean isLoading = false;
    private boolean hasMorePages = true;

    @Inject
    GetJobsUseCase getJobsUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewjob);

        bindingViews();
        setupFilterButton();
        setupRecyclerView();
        BindingAction();
        try {
            fetchJobsFromApi();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void BindingAction() {
        Add_btn.setOnClickListener(v -> {
           Intent intent = new Intent(ViewJobActivity.this, JobActivity.class);
            startActivity(intent);
        });
    }

    private void bindingViews() {
        recyclerViewJobs = findViewById(R.id.recyclerViewJobs);
        spinnerSortBy = findViewById(R.id.spinnerSortBy);
        spinnerSortOrder = findViewById(R.id.spinnerSortOrder);
        editTextFilter = findViewById(R.id.editTextFilter);
        buttonApplyFilter = findViewById(R.id.buttonApplyFilter);
        progressBar = findViewById(R.id.progressBar);
        Add_btn = findViewById(R.id.buttonAdd);
    }

    private void setupFilterButton() {
        buttonApplyFilter.setOnClickListener(v -> {
            String keyword = editTextFilter.getText().toString().trim();
            String sortBy = spinnerSortBy.getSelectedItem().toString();
            boolean isDescending = spinnerSortOrder.getSelectedItem().toString().equals("Descending");
            pageIndex = 1;
            hasMorePages = true;
            try {
                fetchJobsFromApi(keyword, sortBy, isDescending);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setupRecyclerView() {
        recyclerViewJobs.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewJobs.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && !isLoading && hasMorePages &&
                        layoutManager.findLastVisibleItemPosition() >= viewJobAdapter.getItemCount() - 3) {
                    pageIndex++;
                    try {
                        fetchJobsFromApi();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void fetchJobsFromApi() throws IOException {
        fetchJobsFromApi("", "", false);
    }

    private void fetchJobsFromApi(String filter, String sortBy, boolean isDescending) throws IOException {
        if (!hasMorePages) return;
        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);

        Disposable getJobsDisposable =
                getJobsUseCase.execute(pageIndex, pageSize, sortBy, isDescending, filter)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(resp -> {
                            if (resp.getStatus()>=200 && resp.getStatus()<300) {
                                if(resp.getStatus()==204)
                                {
                                    showToast("No jobs found");
                                }else{
                                    if (viewJobAdapter == null) {
                                        viewJobAdapter = new ViewJobAdapter(this, resp.getData().getItems());
                                        recyclerViewJobs.setAdapter(viewJobAdapter);
                                    } else {
                                        viewJobAdapter.addJobs(resp.getData().getItems());
                                    }
                                    hasMorePages = resp.getData().getHasNextPage();
                                }
                            } else {
                                showToast(resp.getMessage());
                            }
                            isLoading = false;
                            progressBar.setVisibility(View.GONE);
                        }, throwable -> {
                            showToast(throwable.getMessage());
                            isLoading = false;
                            progressBar.setVisibility(View.GONE);
                        });

        disposables.add(getJobsDisposable);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}
