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

public class BaseActivity extends AppCompatActivity {

    private final Map<Integer, Class<?>> menuActivityMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingMenu();

    }

    private void bindingMenu(){
        menuActivityMap.put(R.id.nav_home, HomeActivity.class);
        menuActivityMap.put(R.id.nav_manage_job, ViewJobActivity.class);
        menuActivityMap.put(R.id.nav_login, LoginActivity.class);
        menuActivityMap.put(R.id.nav_register, RegisterActivity.class);
        //menuActivityMap.put(R.id.nav_logout, RegisterActivity.class);
        menuActivityMap.put(R.id.nav_manage_transaction, TopUpHistoryActivity.class);

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

        return super.onOptionsItemSelected(item);
    }
}