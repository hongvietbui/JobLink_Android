package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.SE1730.Group3.JobLink.R;

import java.util.Arrays;
import java.util.List;

public class WithdrawActivity extends BaseBottomActivity {

    private final List<String> bankList = Arrays.asList(
            "MB - NH TMCP QUAN DOI",
            "VIETCOMBANK - NH TMCP NGOAI THUONG VIET NAM (VCB)",
            "VIETINBANK - NH TMCP CONG THUONG VIET NAM",
            "ABBANK - NH TMCP AN BINH (ABB)",
            "ACB - NH TMCP A CHAU",
            "AGRIBANK - NH NN - PTNT VIET NAM",
            "BANVIET - NH TMCP BAN VIET",
            "BAOVIETBANK - NH TMCP BAO VIET (BVB)",
            "BIDV - NH DAU TU VA PHAT TRIEN VIET NAM",
            "CBBANK - NH TM TNHH MTV XAY DUNG VIET NAM",
            "CITIBANK VIETNAM - NH TNHH MTV CITIBANK VIET NAM",
            "DONGABANK - NH TMCP DONG A",
            "EXIMBANK - NH TMCP XUAT NHAP KHAU VIET NAM (EIB)",
            "GPBANK - NH TMCP GUANGZHOU (VIET NAM)",
            "HDBANK - NH TMCP PHAT TRIEN TP HO CHI MINH (HDB)",
            "HSBC VIETNAM - NH TNHH MTV HANG HAI (VIET NAM)",
            "INDOVINABANK - NH TMCP INDONESIA VIET NAM (IVB)",
            "KIENLONGBANK - NH TMCP KIEN LONG",
            "LIENVIETPOSTBANK - NH TMCP BUU DIEN LIEN VIET",
            "MARIANNA BANK - NH TMCP ĐẦU TƯ VÀ PHÁT TRIEN NAM VIỆT",
            "MSB - NH TMCP HANG HAI (MSB)",
            "NAMABANK - NH TMCP NAM A",
            "NCB - NH TMCP QUOC DAN",
            "OCB - NH TMCP PHUONG DONG (OCB)",
            "PGBANK - NH TMCP XUONG TRANG VA XAY DUNG VIET NAM",
            "PVCOMBANK - NH TMCP DAU TU VA PHAT TRIEN VIET NAM (PVCOMBANK)",
            "SAIGONBANK - NH TMCP SAI GON (SCB)",
            "SACOMBANK - NH TMCP SAI GON THUONG TIN",
            "SEABANK - NH TMCP DONG NAM A (SEABANK)",
            "SHB - NH TMCP SAIGON HA NOI",
            "SHINHANBANK - NH TNHH MTV SHINHAN VIET NAM",
            "TECHCOMBANK - NH TMCP KY THUAT VIET NAM (TCB)",
            "TPBANK - NH TMCP TIEN PHONG",
            "TRUSTBANK - NH TMCP TIN NGHIA",
            "VIB - NH TMCP QUOC TE VIET",
            "VRB - NH TMCP KHOANG SAN VIET NAM",
            "WOORIBANK - NH TMCP WOORI HAN VIE"
    );
    
    private Spinner mySpinner;
    private ArrayAdapter<String> adapter;
    private EditText etMoney, etAccountNum, etAccountName;
    private Button btnWithdraw;

    private void bindingView(){
        mySpinner = findViewById(R.id.mySpinner);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bankList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mySpinner.setAdapter(adapter);
        
        etMoney = findViewById(R.id.etMoney);
        etAccountNum = findViewById(R.id.etAccountNum);
        etAccountName = findViewById(R.id.etAccountName);
        
        btnWithdraw = findViewById(R.id.btnWithdraw);
    }
    
    private void bindingAction(){
        btnWithdraw.setOnClickListener(this::onBtnWithdrawClick);
    }

    private void onBtnWithdrawClick(View view) {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContent(R.layout.activity_withdraw);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();

    }
}