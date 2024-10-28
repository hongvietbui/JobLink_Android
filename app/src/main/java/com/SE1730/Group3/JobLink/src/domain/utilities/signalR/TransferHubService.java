package com.SE1730.Group3.JobLink.src.domain.utilities.signalR;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.SE1730.Group3.JobLink.src.data.models.all.TransactionDTO;
import com.SE1730.Group3.JobLink.src.presentation.adapters.BigDecimalAdapter;
import com.SE1730.Group3.JobLink.src.presentation.adapters.UUIDJsonAdapter;
import com.SE1730.Group3.JobLink.src.presentation.fragments.TransferSuccessDialog;
import com.microsoft.signalr.HubConnection;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

public class TransferHubService {
    private HubConnection hubConnection;
    private Context context;
    private Moshi moshi = new Moshi
            .Builder()
            .add(new BigDecimalAdapter())
            .add(new UUIDJsonAdapter())
            .build();
    private JsonAdapter<TransactionDTO> transactionAdapter = moshi.adapter(TransactionDTO.class);

    public TransferHubService(HubConnection hubConnection, Context context) {
        this.hubConnection = hubConnection;
        this.context = context;
        setupTransferHub();
    }

    private void setupTransferHub() {
        hubConnection.on("ReceiveTransfer", (user, message) -> {
            try {
                // Deserialize JSON string to TransactionDTO
                TransactionDTO transactionDTO = transactionAdapter.fromJson(message);
                if (transactionDTO != null) {
                    showTransferDialog(transactionDTO);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, String.class, String.class);

        hubConnection.start().doOnComplete(() -> Log.d("TransferHubService", "TransferHub Connected")).subscribe();
    }

    private void showTransferDialog(TransactionDTO transactionDTO) {
        new Handler(Looper.getMainLooper()).post(() -> {
            TransferSuccessDialog dialog = new TransferSuccessDialog();
            Bundle args = new Bundle();
            args.putString("amount", transactionDTO.getAmount().toString());
            dialog.setArguments(args);
            dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "TransferSuccessDialog");
        });
    }
}
