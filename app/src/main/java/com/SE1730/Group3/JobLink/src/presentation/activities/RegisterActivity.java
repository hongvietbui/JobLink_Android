package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.domain.utilities.Validator;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.RegisterViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.io.IOException;
import java.util.Calendar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterActivity extends BaseActivity {
    private RegisterViewModel registerViewModel;
    private EditText edtUsername, edtEmail, edtPhoneNumber, edtFirstName, edtLastName, edtAddress;

    private TextInputLayout tilPassword, tilRepassword;
//    private ImageView ivIsPasswordShow, ivIsRepasswordShow;

    private EditText edtDateOfBirth;
    private Button btnRegister;
    private TextView tvLogin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        bindingViews();
        setEvents();
    }

    private void bindingViews(){
        edtUsername = findViewById(R.id.edtUsername);

        tilPassword = findViewById(R.id.tilPassword);
        tilRepassword = findViewById(R.id.tilRepassword);
//        ivIsPasswordShow = findViewById(R.id.ivIsPasswordShow);
//        ivIsRepasswordShow = findViewById(R.id.ivIsRepasswordShow);

        edtEmail = findViewById(R.id.edtEmail);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtAddress = findViewById(R.id.edtAddress);

        edtDateOfBirth = findViewById(R.id.edtDateOfBirth);

        btnRegister = findViewById(R.id.btnRegister);

        tvLogin = findViewById(R.id.tvLogin);
    }

    private void setEvents(){
        edtDateOfBirth.setOnClickListener(v -> openDatePickerDialog());
        btnRegister.setOnClickListener(v -> register());
        tvLogin.setOnClickListener(v -> openLoginActivity());
    }

    private void register(){
        try {
            if(!checkFormValidation()){
                return;
            }

            registerViewModel.RegisterUser(edtUsername.getText().toString(),
                    edtEmail.getText().toString(),
                    tilRepassword.getEditText().getText().toString(),
                    edtFirstName.getText().toString(),
                    edtLastName.getText().toString(),
                    edtPhoneNumber.getText().toString(),
                    edtAddress.getText().toString(),
                    LocalDate.parse(edtDateOfBirth.getText().toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            handleRegisterResult();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void openLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void openDatePickerDialog(){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            edtDateOfBirth.setText(date);
        }, year, month, day);

        datePickerDialog.show();
    }

    private void handleRegisterResult(){
        registerViewModel.registerResult.observe(this, result -> {
            if(result!=null){
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                Snackbar.make(findViewById(android.R.id.content), result.getMessage(), Snackbar.LENGTH_SHORT).show();
            }else{
                Snackbar.make(findViewById(android.R.id.content), "Failed to register user", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean checkFormValidation(){
        boolean result = true;

        if(Validator.areFieldsNullOrEmpty(edtUsername, edtEmail,
                edtPhoneNumber, edtFirstName, edtLastName, edtAddress))
            result = false;

        if(Validator.areFieldsNullOrEmpty(tilPassword, tilRepassword))
            result = false;

        //Todo: Fix date of birth issue
        if(Validator.isNullOrEmpty(edtDateOfBirth)){
            edtDateOfBirth.setText("10/10/2003");
        }

        return result;
    }
}
