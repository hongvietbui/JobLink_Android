package com.SE1730.Group3.JobLink.src.presentation.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.SE1730.Group3.JobLink.databinding.FragmentTransferSuccessBinding;

public class TransferSuccessDialog extends DialogFragment {
    private FragmentTransferSuccessBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTransferSuccessBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Thiết lập các thông báo trong Dialog
        binding.tvSuccessMessage.setText("Transfer successfully!");
        binding.tvAmount.setText("Money: ______ VND");
        binding.tvThankYou.setText("Thank you for choosing our app!");

        // Khởi động countdown timer
        new CountDownTimer(5000, 50) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) ((5000 - millisUntilFinished) / 50);
                binding.progressBarCountdown.setProgress(progress);
            }

            @Override
            public void onFinish() {
                dismiss(); // Đóng Dialog khi đếm ngược hoàn tất
            }
        }.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
