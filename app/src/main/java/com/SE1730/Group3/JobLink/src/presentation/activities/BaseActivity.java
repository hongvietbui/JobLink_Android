package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.SE1730.Group3.JobLink.R;

import java.util.HashMap;
import java.util.Map;

public class BaseActivity extends AppCompatActivity {

    private final Map<Integer, Class<?>> menuActivityMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingMenu();
    }

    private void bindingMenu() {
        menuActivityMap.put(R.id.nav_home, HomeActivity.class);
        menuActivityMap.put(R.id.nav_manage_job, JobManagementNavigationActivity.class);
        menuActivityMap.put(R.id.nav_manage_transaction, TopUpHistoryActivity.class);
        menuActivityMap.put(R.id.nav_user_stat, UserStatActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Class<?> activityClass = menuActivityMap.get(item.getItemId());

        if (activityClass != null) {
            Intent intent = new Intent(this, activityClass);
            startActivity(intent);
            return true;
        }

        if (item.getItemId() == R.id.nav_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Phương thức đăng xuất
    private void logout() {
        // Điều hướng về màn hình đăng nhập hoặc thực hiện logic đăng xuất nếu cần
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
