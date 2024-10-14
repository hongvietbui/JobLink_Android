package com.SE1730.Group3.JobLink.src.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.RegisterViewModel;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;

import java.io.IOException;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment extends Fragment {
    private RegisterViewModel registerViewModel;
    private EditText edtUsername, edtEmail, edtPassword, edtFirstName, edtLastName, edtPhoneNumber, edtAddress, edtDateOfBirth;
    private Button btnRegister;

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        AndroidThreeTen.init(this.getActivity());

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        bindingViews(view);

        setEvents();

        return view;
    }

    private void bindingViews(View view){
        edtUsername = view.findViewById(R.id.edtUsername);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPassword = view.findViewById(R.id.edtPassword);
        edtFirstName = view.findViewById(R.id.edtFirstName);
        edtLastName = view.findViewById(R.id.edtLastName);
        edtPhoneNumber = view.findViewById(R.id.edtPhoneNumber);
        edtAddress = view.findViewById(R.id.edtAddress);
        edtDateOfBirth = view.findViewById(R.id.edtDateOfBirth);
        btnRegister = view.findViewById(R.id.btnRegister);
    }

    private void setEvents(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    registerViewModel.RegisterUser(edtUsername.getText().toString(),
                            edtEmail.getText().toString(),
                            edtPassword.getText().toString(),
                            edtFirstName.getText().toString(),
                            edtLastName.getText().toString(),
                            edtPhoneNumber.getText().toString(),
                            edtAddress.getText().toString(),
                            LocalDate.now());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        registerViewModel.registerResult.observe(getViewLifecycleOwner(), result -> {
            if(result!=null){
                Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "Failed to register user", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
