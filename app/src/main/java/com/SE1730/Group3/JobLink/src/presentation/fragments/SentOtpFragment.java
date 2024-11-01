package com.SE1730.Group3.JobLink.src.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.presentation.activities.ResetPasswordActivity;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.SentOtpViewModel;

import java.io.IOException;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SentOtpFragment extends Fragment {

    private EditText editTextEmail;
    private Button buttonSendOtp;
    private SentOtpViewModel sentOtpViewModel;

    private ResetPasswordActivity resetPasswordActivity;

    @Nullable
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ResetPasswordActivity) {
            resetPasswordActivity = (ResetPasswordActivity) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ResetPasswordActivity");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sent_otp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sentOtpViewModel = new ViewModelProvider(this).get(SentOtpViewModel.class);

        BindingView(view);
        BindingAction();
    }

    private void BindingView(View view) {
        editTextEmail = view.findViewById(R.id.editText_email);
        buttonSendOtp = view.findViewById(R.id.button_send_otp);
    }

    private void BindingAction() {
        buttonSendOtp.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            try {
                sentOtpViewModel.sendOtp(email);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        sentOtpViewModel.otpconfirmResult.observe(getViewLifecycleOwner(), new Observer<ApiResp<String>>() {
            @Override
            public void onChanged(ApiResp<String> apiResp) {
                if (apiResp != null && apiResp.getStatus() != null && apiResp.getStatus() == 200) {
                    resetPasswordActivity.setEmail(editTextEmail.getText().toString().trim());
                    Toast.makeText(getContext(), "OTP sent successfully!", Toast.LENGTH_SHORT).show();
                    resetPasswordActivity.loadVerifyOtpFragment();
                } else if (apiResp != null) {
                    Toast.makeText(getContext(), "Failed to send OTP. Please try again.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("SentOtpFragment", "ApiResp is null.");
                    Toast.makeText(getContext(), "An unexpected error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
