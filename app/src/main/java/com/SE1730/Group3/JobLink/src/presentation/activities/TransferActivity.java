package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.domain.useCases.GetQRCodeByUserIdUseCase;
import com.SE1730.Group3.JobLink.src.presentation.fragments.TransferSuccessDialog;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.val;

@AndroidEntryPoint
public class TransferActivity extends BaseActivity {
    @Inject
    GetQRCodeByUserIdUseCase getQRCodeByUserIdUseCase;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private ImageView ivQrCode;

    private BroadcastReceiver showDialogReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String amount = intent.getStringExtra("amount");

            var dialog = TransferSuccessDialog.newInstance(amount);
            dialog.show(getSupportFragmentManager(), "TransferSuccessDialog");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        bindingViews();
    }

    private void bindingViews() {
        LocalBroadcastManager.getInstance(this).registerReceiver(showDialogReceiver, new IntentFilter("SHOW_TRANSFER_SUCCESS_DIALOG"));

        ivQrCode = findViewById(R.id.ivQRCode);

        var getQrCodeDisposable = getQRCodeByUserIdUseCase.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
                    if (resp.getStatus() == 200) {
                        loadBase64QRCode(this, resp.getData(), ivQrCode);
                    } else {
                        // Hiển thị thông báo lỗi
                        Snackbar.make(ivQrCode, resp.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }, Throwable::printStackTrace);

        disposables.add(getQrCodeDisposable);
    }

    private void loadBase64QRCode(Context context, String qrCode, ImageView imageView) {
        val decodedString = android.util.Base64.decode(qrCode.split(",")[1], android.util.Base64.DEFAULT);

        val bitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        Picasso.get()
                .load(getBitMapUri(context, bitMap))
                .into(imageView);
    }

    private Uri getBitMapUri(Context context, Bitmap bitmap){
        Uri uri = null;
        try{
            File file = new File(context.getCacheDir(), "qr.png");
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.flush();
            stream.close();
            uri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(showDialogReceiver);
    }
}
