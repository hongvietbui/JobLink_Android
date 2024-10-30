package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.SE1730.Group3.JobLink.R; // Ensure you import your resource correctly
import com.SE1730.Group3.JobLink.src.presentation.fragments.OwnerManageJobFragment;
import com.SE1730.Group3.JobLink.src.presentation.fragments.WorkerManageJobFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UserManageJobActivity extends AppCompatActivity {

    private ViewPager2 viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage_job);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(this));
    }

    private class ViewPagerAdapter extends FragmentStateAdapter {
        public ViewPagerAdapter(@NonNull UserManageJobActivity fragmentActivity) {
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
            return 2; // Two fragments: Owner and Worker
        }
    }
}
