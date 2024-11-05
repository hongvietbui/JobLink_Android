package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.presentation.fragments.OwnerManageJobFragment;
import com.SE1730.Group3.JobLink.src.presentation.fragments.WorkerManageJobFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class JobManagementNavigationActivity extends BaseBottomActivity {

    private ViewPager2 viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_job_management_navigation);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(this));
    }

    private class ViewPagerAdapter extends FragmentStateAdapter {
        public ViewPagerAdapter(@NonNull JobManagementNavigationActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new OwnerManageJobFragment();
            } else {
                return new WorkerManageJobFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
