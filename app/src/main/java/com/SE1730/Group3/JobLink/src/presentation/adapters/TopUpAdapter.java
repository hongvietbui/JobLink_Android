package com.SE1730.Group3.JobLink.src.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.data.models.all.TopUpDTO;

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

        holder.dateTimeTextView.setText(topUp.getTransactionDate().toString());
        holder.amountTextView.setText(topUp.getAmount().toString());
        holder.statusTextView.setText(topUp.getStatus());
        holder.paymentTypeTextView.setText(topUp.getPaymentType());
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
