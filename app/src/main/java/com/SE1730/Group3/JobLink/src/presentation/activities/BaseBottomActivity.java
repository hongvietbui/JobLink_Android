package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.SE1730.Group3.JobLink.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseBottomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base); // Layout cơ bản chứa BottomNavigationView

        // Thiết lập BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            // Sử dụng if-else để kiểm tra item được chọn
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                if (!this.getClass().equals(HomeActivity.class)) {
                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                }
                return true;
            } else if (itemId == R.id.navigation_activity) {
                if (!this.getClass().equals(JobManagementNavigationActivity.class)) {
                    startActivity(new Intent(this, JobManagementNavigationActivity.class));
                    finish();
                }
                return true;
            } else if (itemId == R.id.navigation_messages) {
                if (!this.getClass().equals(HomeActivity.class)) {
                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                }
                return true;
            } else if (itemId == R.id.navigation_account) {
                if (!this.getClass().equals(HomeActivity.class)) {
                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                }
                return true;
            } else if (itemId == R.id.navigation_notification) {
                if (!this.getClass().equals(NotificationActivity.class)) {
                    startActivity(new Intent(this, NotificationActivity.class));
                    finish();
                }
                return true;
            }
            return false;
        });
    }

    // Hàm để đặt item được chọn cho Activity hiện tại
    protected void setSelectedNavigationItem(int itemId) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(itemId);
    }

    // Hàm để chèn layout cụ thể của Activity con vào FrameLayout trong activity_base.xml
    protected void setContent(int layoutResID) {
        getLayoutInflater().inflate(layoutResID, findViewById(R.id.frame_content), true);
    }
}
