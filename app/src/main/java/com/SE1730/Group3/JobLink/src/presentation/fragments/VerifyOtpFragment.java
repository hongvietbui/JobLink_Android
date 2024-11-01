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
import com.SE1730.Group3.JobLink.src.presentation.viewModels.VerifyOtpViewModel;

import java.io.IOException;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint

public class VerifyOtpFragment extends Fragment {

    private EditText otpText;
    private Button buttonVerifyOtp;
    private VerifyOtpViewModel verifyOtpViewModel;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_verify_otp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        verifyOtpViewModel = new ViewModelProvider(this).get(VerifyOtpViewModel.class);

        BindingView(view);
        BindingAction();
    }

    private void BindingView(View view) {
        otpText = view.findViewById(R.id.editText_otp);
        buttonVerifyOtp = view.findViewById(R.id.button_verify_otp);
    }

    private void BindingAction() {
        buttonVerifyOtp.setOnClickListener(v -> {
            String code = otpText.getText().toString().trim();
            String email = resetPasswordActivity.getEmail();

            try {
                verifyOtpViewModel.VerifyOtp(email, code);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Observing the verification result
            verifyOtpViewModel.otpconfirmverifyResult.observe(getViewLifecycleOwner(), new Observer<ApiResp<String>>() {
                @Override
                public void onChanged(ApiResp<String> apiResp) {
                    if (apiResp != null && apiResp.getStatus() != null && apiResp.getStatus() == 200) {
                        Toast.makeText(getContext(), "OTP verified successfully!", Toast.LENGTH_SHORT).show();
                        resetPasswordActivity.showResetPasswordLayout();
                    } else if (apiResp != null) {
                        Toast.makeText(getContext(), "Invalid OTP. Please try again.", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("VerifyOtpFragment", "ApiResp is null.");
                        Toast.makeText(getContext(), "An unexpected error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        });
    }
}
