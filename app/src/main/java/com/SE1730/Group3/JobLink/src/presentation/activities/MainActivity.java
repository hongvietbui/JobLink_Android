package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.presentation.fragments.RegisterFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindingViews();
        setEvents();
    }

    private void bindingViews(){
        btnRegister = findViewById(R.id.btnRegister);
    }

    private void setEvents(){
        btnRegister.setOnClickListener(v -> {
            openFragment(new RegisterFragment());
        });
    }

    private void openFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
