package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.SE1730.Group3.JobLink.R;

import java.util.HashMap;
import java.util.Map;
import android.content.SharedPreferences;

public class BaseActivity extends AppCompatActivity {

    private final Map<Integer, Class<?>> menuActivityMap = new HashMap<>();
    private boolean isLoggedIn = false; // Trạng thái đăng nhập

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingMenu();
        checkLoginStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLoginStatus();
    }

    private void checkLoginStatus() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        if(prefs.getString("accessToken", null) != null){

            isLoggedIn = true;
        }
        invalidateOptionsMenu(); // Refresh menu to apply updated login state
    }

    private void bindingMenu(){
        menuActivityMap.put(R.id.nav_home, HomeActivity.class);
        menuActivityMap.put(R.id.nav_manage_job, ViewJobActivity.class);
        menuActivityMap.put(R.id.nav_login, LoginActivity.class);
        menuActivityMap.put(R.id.nav_register, RegisterActivity.class);
        menuActivityMap.put(R.id.nav_manage_transaction, TopUpHistoryActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        updateMenuVisibility(menu); // Update menu based on login state
        return true;
    }

    private void updateMenuVisibility(Menu menu) {
        menu.findItem(R.id.nav_logout).setVisible(isLoggedIn);
        menu.findItem(R.id.nav_manage_job).setVisible(isLoggedIn);
        menu.findItem(R.id.nav_manage_transaction).setVisible(isLoggedIn);
        menu.findItem(R.id.nav_login).setVisible(!isLoggedIn);
        menu.findItem(R.id.nav_register).setVisible(!isLoggedIn);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Class<?> activityClass = menuActivityMap.get(item.getItemId());

        if (activityClass != null) {
            Intent intent = new Intent(this, activityClass);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Phương thức này để gọi khi đăng nhập thành công
    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
        invalidateOptionsMenu(); // Yêu cầu cập nhật lại menu
    }
}
