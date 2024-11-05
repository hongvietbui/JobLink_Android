package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.ChangePassViewModel;

import java.io.IOException;
import java.util.UUID;

public class ChangePassActivity extends BaseBottomActivity {

    private EditText edtCurPass, edtNewPass, edtConfirmPass;
    private Button btnChangePass;
    private ChangePassViewModel changePassViewModel;

    private void bindingView(){
        edtCurPass = findViewById(R.id.edtCurPass);
        edtNewPass = findViewById(R.id.edtNewPass);
        edtConfirmPass = findViewById(R.id.edtConfirmPass);
        btnChangePass = findViewById(R.id.btnChangePassword);
        changePassViewModel = new ViewModelProvider(this).get(ChangePassViewModel.class);
    }

    private void bindingAction(){
        btnChangePass.setOnClickListener(this::onBtnChangePassClick);
    }

    private void onBtnChangePassClick(View view) {
        try {
            String curPass = edtCurPass.getText().toString().trim();
            String newPass = edtNewPass.getText().toString().trim();
            String confirmPass = edtConfirmPass.getText().toString().trim();

            // Kiểm tra nếu mật khẩu hiện tại và mật khẩu mới khác nhau
            if (curPass.equals(newPass)) {
                edtNewPass.setError("The new password cannot be the same as the current password.");
                return;
            }

            // Kiểm tra nếu mật khẩu mới và xác nhận mật khẩu giống nhau
            if (!newPass.equals(confirmPass)) {
                edtConfirmPass.setError("Confirmation password does not match");
                return;
            }


            // Lấy userId (Giả sử bạn lấy từ SharedPreferences)
            UUID userId = getUserIdFromSharedPreferences();  // Implement this method

            // Gọi ViewModel để thực hiện logic thay đổi mật khẩu
            changePassViewModel.ChangePassUser(userId, curPass, newPass);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        changePassViewModel.changePassResult.observe(this, result -> {
            if(result!=null){
                Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Failed to change password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private UUID getUserIdFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userIdString = sharedPreferences.getString("userId", null);

        if (userIdString != null) {
            return UUID.fromString(userIdString);  // Chuyển đổi chuỗi về UUID
        } else {
            return null;  // Xử lý trường hợp userId không tồn tại
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContent(R.layout.activity_change_pass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
    }
}