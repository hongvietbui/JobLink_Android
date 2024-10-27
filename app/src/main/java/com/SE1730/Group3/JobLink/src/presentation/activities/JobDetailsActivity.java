package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.presentation.adapters.ViewPagerAdapter;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class JobDetailsActivity extends AppCompatActivity {
    private ViewPager2 viewPager;

    private ImageButton btnLeft, btnRight;
    private Button btnMap, btnImage, btnDetails;
    private ImageView ivJobOwner;
    private TextView tvName, tvRating, tvLocation, tvNote;
    private Button btnAccept, btnCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        onBinding();
        setEvents();
    }

    private void onBinding(){
        viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);

        btnMap = findViewById(R.id.btnMap);
        btnImage = findViewById(R.id.btnImage);
        btnDetails = findViewById(R.id.btnDetails);

        ivJobOwner = findViewById(R.id.ivJobOwner);

        tvName = findViewById(R.id.tvName);
        tvRating = findViewById(R.id.tvRating);
        tvLocation = findViewById(R.id.tvLocation);
        tvNote = findViewById(R.id.tvNote);

        btnAccept = findViewById(R.id.btnAccept);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void setEvents(){
        btnLeft.setOnClickListener(v -> viewPager.setCurrentItem(viewPager.getCurrentItem() - 1));
        btnRight.setOnClickListener(v -> viewPager.setCurrentItem(viewPager.getCurrentItem() + 1));

        btnImage.setOnClickListener(v -> {
            viewPager.setCurrentItem(0, false);
            updateButtonStyles(0);
        });

        btnMap.setOnClickListener(v -> {
            viewPager.setCurrentItem(1, false);
            updateButtonStyles(1);
        });

        btnDetails.setOnClickListener(v -> {
            viewPager.setCurrentItem(2, false);
            updateButtonStyles(2);
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
    }

    private void updateButtonStyles(int selectedButton) {
        btnImage.setBackgroundColor(getResources().getColor(R.color.cyne10));
        btnMap.setBackgroundColor(getResources().getColor(R.color.green10));
        btnDetails.setBackgroundColor(getResources().getColor(R.color.aquamarine10));

        switch (selectedButton) {
            case 0:
                btnImage.setBackgroundColor(getResources().getColor(R.color.cyne15));
                break;
            case 1:
                btnMap.setBackgroundColor(getResources().getColor(R.color.green15));
                break;
            case 2:
                btnDetails.setBackgroundColor(getResources().getColor(R.color.aquamarine15));
                break;
        }
    }
}
