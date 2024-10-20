package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.SE1730.Group3.JobLink.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private Button btnRegister, btnJobDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindingViews();
        setEvents();
    }

    private void bindingViews(){
        btnRegister = findViewById(R.id.btnRegister);
        btnJobDetails = findViewById(R.id.btnJobDetails);
    }

    private void setEvents(){
        btnRegister.setOnClickListener(v -> openRegisterActivity());
        btnJobDetails.setOnClickListener(v -> openJobDetailsActivity());
    }

    private void openFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void openJobDetailsActivity(){
        Intent intent = new Intent(this, JobDetailsActivity.class);
        startActivity(intent);
    }
}
