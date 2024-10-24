package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.presentation.fragments.SentOtpFragment;
import com.SE1730.Group3.JobLink.src.presentation.fragments.VerifyOtpFragment;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.ResetForgotPassViewModel;

import java.io.IOException;

import dagger.hilt.android.AndroidEntryPoint;
import lombok.Getter;
import lombok.Setter;

@AndroidEntryPoint
public class ResetPasswordActivity extends AppCompatActivity {
    @Getter
    @Setter
    private String email;
    private EditText editTextNewPassword;
    private EditText editTextConfirmPassword;
    private Button buttonResetPassword;
    private ResetForgotPassViewModel resetForgotPassViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        resetForgotPassViewModel = new ViewModelProvider(this).get(ResetForgotPassViewModel.class); 
        findViewById(R.id.reset_password_layout).setVisibility(View.GONE);
        loadSentOtpFragment();
        bindingView();
        bindingAction();
    }

    private void bindingAction() {
        buttonResetPassword.setOnClickListener(view -> {
            String newPassword = editTextNewPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                editTextNewPassword.setError("Password field cannot be empty");
                editTextConfirmPassword.setError("Confirm password field cannot be empty");
            } else if (!newPassword.equals(confirmPassword)) {
                editTextConfirmPassword.setError("Passwords do not match");
            } else {
                try {
                    String email = getEmail();
                    resetForgotPassViewModel.ResetPass(email, newPassword);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        resetForgotPassViewModel.ResetPassResult.observe(this, apiResp -> {
            if (apiResp != null && apiResp.getData() != null) {
                Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to reset password: " + apiResp.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void bindingView() {
        editTextNewPassword = findViewById(R.id.editText_new_password);
        editTextConfirmPassword = findViewById(R.id.editText_confirm_password);
        buttonResetPassword = findViewById(R.id.button_reset_password);
    }

    private void loadSentOtpFragment() {
        SentOtpFragment sentOtpFragment = new SentOtpFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_OtpContainer, sentOtpFragment);
        fragmentTransaction.commit();
    }

    public void loadVerifyOtpFragment() {
        VerifyOtpFragment verifyOtpFragment = new VerifyOtpFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_OtpContainer, verifyOtpFragment);
        fragmentTransaction.commit();
    }

    public void showResetPasswordLayout() {
        findViewById(R.id.reset_password_layout).setVisibility(View.VISIBLE); // Show the reset password layout
        findViewById(R.id.fragment_OtpContainer).setVisibility(View.GONE); // Hide the fragment
    }
}
