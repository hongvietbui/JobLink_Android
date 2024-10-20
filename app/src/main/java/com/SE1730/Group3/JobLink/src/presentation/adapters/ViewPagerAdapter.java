package com.SE1730.Group3.JobLink.src.presentation.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.SE1730.Group3.JobLink.src.presentation.fragments.JobDetailsFragment;
import com.SE1730.Group3.JobLink.src.presentation.fragments.JobImageFragment;
import com.SE1730.Group3.JobLink.src.presentation.fragments.MapFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new JobImageFragment();
            case 1:
                return new MapFragment();
            case 2:
                return new JobDetailsFragment();
            default:
                throw new IllegalStateException("Invalid position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
