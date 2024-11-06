package com.SE1730.Group3.JobLink.src.presentation.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.threeten.bp.format.DateTimeFormatter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.data.models.all.TopUpDTO;
import com.SE1730.Group3.JobLink.src.domain.enums.PaymentStatus;
import com.SE1730.Group3.JobLink.src.domain.enums.PaymentType;

import java.util.ArrayList;
import java.util.List;

public class TopUpAdapter extends RecyclerView.Adapter<TopUpAdapter.TopUpViewHolder> {
    private List<TopUpDTO> topUpList;
    private Context context;

    public TopUpAdapter(Context context, List<TopUpDTO> topUpList) {
        this.context = context;
        this.topUpList = new ArrayList<>(topUpList);
    }

    public TopUpAdapter() {
        this.topUpList = new ArrayList<>();
    }

    public void setData(List<TopUpDTO> newTopUpList) {
        this.topUpList.clear();
        this.topUpList.addAll(newTopUpList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TopUpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topup_list, parent, false);
        return new TopUpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopUpViewHolder holder, int position) {
        TopUpDTO topUp = topUpList.get(position);

        // Định dạng DateTime
        if (topUp.getTransactionDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formattedDate = topUp.getTransactionDate().format(formatter);
            holder.dateTimeTextView.setText("Date&Time: " + formattedDate);
        } else {
            holder.dateTimeTextView.setText("Unavailable date");
        }

        // Chuyển đổi và hiển thị Payment Status
        int statusValue = Integer.parseInt(topUp.getStatus());
        PaymentStatus status = PaymentStatus.fromInt(statusValue);
        String statusDisplayName = (status != null) ? status.getDisplayName() : "Unknown";
        holder.statusTextView.setText("Status: " + statusDisplayName);

        // Chuyển đổi và hiển thị Payment Type
        int typeValue = Integer.parseInt(topUp.getPaymentType());
        PaymentType type = PaymentType.fromInt(typeValue);
        String typeDisplayName = (type != null) ? type.getDisplayName() : "Unknown";
        holder.paymentTypeTextView.setText("Type: " + typeDisplayName);

        if (type == PaymentType.WITHDRAW) {
            holder.paymentTypeTextView.setTextColor(Color.RED);
            holder.amountTextView.setText("Amount: -" + topUp.getAmount().toString());
        } else if (type == PaymentType.DEPOSIT) {
            holder.paymentTypeTextView.setTextColor(Color.GREEN);
            holder.amountTextView.setText("Amount: +" + topUp.getAmount().toString());
        }

        if (status == PaymentStatus.DONE) {
            holder.statusTextView.setTextColor(Color.GREEN);
        } else if (status == PaymentStatus.PENDING) {
            holder.statusTextView.setTextColor(Color.YELLOW);
        } else if (status == PaymentStatus.REJECTED) {
            holder.statusTextView.setTextColor(Color.RED);
        }
    }


    @Override
    public int getItemCount() {
        return topUpList.size();
    }

    public static class TopUpViewHolder extends RecyclerView.ViewHolder {
        private TextView dateTimeTextView;
        private TextView amountTextView;
        private TextView statusTextView;
        private TextView paymentTypeTextView;

        public TopUpViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTimeTextView = itemView.findViewById(R.id.TransactionTime);
            amountTextView = itemView.findViewById(R.id.TransactionAmount);
            statusTextView = itemView.findViewById(R.id.TransactionStatus);
            paymentTypeTextView = itemView.findViewById(R.id.TransactionType);
        }
    }
}
