package com.SE1730.Group3.JobLink.src.presentation.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.domain.useCases.GetJobsUseCase;
import com.SE1730.Group3.JobLink.src.presentation.activities.JobActivity;
import com.SE1730.Group3.JobLink.src.presentation.adapters.ViewJobAdapter;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class ViewJobFragment extends Fragment {
    private CompositeDisposable disposables = new CompositeDisposable();

    private RecyclerView recyclerViewJobs;
    private ViewJobAdapter viewJobAdapter;
    private Spinner spinnerSortBy;
    private Spinner spinnerSortOrder;
    private EditText editTextFilter;
    private Button buttonApplyFilter;
    private View progressBar;
    private ImageButton addBtn;
    private int pageIndex = 1;
    private final int pageSize = 10;
    private boolean isLoading = false;
    private boolean hasMorePages = true;

    @Inject
    GetJobsUseCase getJobsUseCase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_job, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindingViews(view);
        setupFilterButton();
        setupRecyclerView();
        bindingActions();
        try {
            fetchJobsFromApi();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void bindingViews(View view) {
        recyclerViewJobs = view.findViewById(R.id.recyclerViewJobs);
        spinnerSortBy = view.findViewById(R.id.spinnerSortBy);
        spinnerSortOrder = view.findViewById(R.id.spinnerSortOrder);
        editTextFilter = view.findViewById(R.id.editTextFilter);
        buttonApplyFilter = view.findViewById(R.id.buttonApplyFilter);
        progressBar = view.findViewById(R.id.progressBar);
        addBtn = view.findViewById(R.id.buttonAdd);
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
        recyclerViewJobs.setLayoutManager(new LinearLayoutManager(getContext()));
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

    private void bindingActions() {
        addBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), JobActivity.class);
            startActivity(intent);
        });
    }

    private void fetchJobsFromApi() throws IOException {
        fetchJobsFromApi("", "", false);
    }

    private void fetchJobsFromApi(String filter, String sortBy, boolean isDescending) throws IOException {
        if (!hasMorePages) return;
        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);

        Disposable getJobsDisposable = getJobsUseCase.execute(pageIndex, pageSize, sortBy, isDescending, filter)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(resp -> {
                    Log.d("ViewJobFragment", "API Response Status: " + resp.getStatus());
                    Log.d("ViewJobFragment", "Data items count: " + (resp.getData() != null ? resp.getData().getItems().size() : 0));

                    if (resp.getStatus() >= 200 && resp.getStatus() < 300) {
                        if (resp.getStatus() == 204 || resp.getData() == null || resp.getData().getItems().isEmpty()) {
                            showToast("No jobs found");
                        } else {
                            if (viewJobAdapter == null) {
                                viewJobAdapter = new ViewJobAdapter(requireContext(), resp.getData().getItems());
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
                    Log.e("ViewJobFragment", "Error loading jobs", throwable);
                    isLoading = false;
                    progressBar.setVisibility(View.GONE);
                });

        disposables.add(getJobsDisposable);
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposables.clear();
    }
}
