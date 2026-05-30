package com.tournament.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tournament.app.R;
import com.tournament.app.model.WalletModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.WalletViewHolder> {

    private Context context;
    private List<WalletModel> transactionList;

    public WalletAdapter(Context context) {
        this.context = context;
        this.transactionList = new ArrayList<>();
    }

    public void setTransactionList(List<WalletModel> transactionList) {
        this.transactionList = transactionList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WalletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);
        return new WalletViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletViewHolder holder, int position) {
        WalletModel transaction = transactionList.get(position);
        holder.bind(transaction);
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    static class WalletViewHolder extends RecyclerView.ViewHolder {
        TextView transactionIdTextView, amountTextView, typeTextView, descriptionTextView, timestampTextView;

        public WalletViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionIdTextView = itemView.findViewById(R.id.transactionIdTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
        }

        public void bind(WalletModel transaction) {
            transactionIdTextView.setText("ID: " + transaction.getTransactionId());
            amountTextView.setText(String.format("Amount: ₹%.2f", transaction.getAmount()));
            typeTextView.setText("Type: " + transaction.getType());
            descriptionTextView.setText("Desc: " + transaction.getDescription());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            timestampTextView.setText(sdf.format(new Date(transaction.getTimestamp())));
        }
    }
}
