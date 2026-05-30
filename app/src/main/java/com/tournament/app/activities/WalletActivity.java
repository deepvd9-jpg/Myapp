package com.tournament.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tournament.app.R;
import com.tournament.app.adapters.WalletAdapter;
import com.tournament.app.api.ApiRepository;
import com.tournament.app.model.WalletModel;
import com.tournament.app.payment.CashfreeManager;
import com.tournament.app.utils.SessionManager;

import java.util.List;
import java.util.Map;

public class WalletActivity extends AppCompatActivity implements CashfreeManager.PaymentCallback {

    private TextView balanceTextView;
    private EditText amountEditText;
    private Button addMoneyButton;
    private RecyclerView transactionHistoryRecyclerView;
    private ProgressBar progressBar;

    private ApiRepository apiRepository;
    private SessionManager sessionManager;
    private CashfreeManager cashfreeManager;
    private WalletAdapter walletAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        balanceTextView = findViewById(R.id.balanceTextView);
        amountEditText = findViewById(R.id.amountEditText);
        addMoneyButton = findViewById(R.id.addMoneyButton);
        transactionHistoryRecyclerView = findViewById(R.id.transactionHistoryRecyclerView);
        progressBar = findViewById(R.id.progressBar);

        apiRepository = new ApiRepository();
        sessionManager = new SessionManager(this);
        cashfreeManager = new CashfreeManager(this, this);

        transactionHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        walletAdapter = new WalletAdapter(this);
        transactionHistoryRecyclerView.setAdapter(walletAdapter);

        addMoneyButton.setOnClickListener(v -> {
            addMoneyToWallet();
        });

        loadWalletData();
    }

    private void loadWalletData() {
        String firebaseUid = sessionManager.getFirebaseUid();
        if (firebaseUid == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        apiRepository.getWalletBalance(firebaseUid, new ApiRepository.ApiCallback<Map<String, Double>>() {
            @Override
            public void onSuccess(Map<String, Double> result) {
                if (result != null && result.containsKey("balance")) {
                    balanceTextView.setText(String.format("Balance: ₹%.2f", result.get("balance")));
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(WalletActivity.this, "Failed to load balance: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        apiRepository.getWalletHistory(firebaseUid, new ApiRepository.ApiCallback<List<WalletModel>>() {
            @Override
            public void onSuccess(List<WalletModel> result) {
                walletAdapter.setTransactionList(result);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(WalletActivity.this, "Failed to load history: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void addMoneyToWallet() {
        String amountStr = amountEditText.getText().toString().trim();
        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);
        if (amount <= 0) {
            Toast.makeText(this, "Amount must be greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }

        String firebaseUid = sessionManager.getFirebaseUid();
        if (firebaseUid == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        apiRepository.createWalletOrder(firebaseUid, amount, new ApiRepository.ApiCallback<Map<String, String>>() {
            @Override
            public void onSuccess(Map<String, String> result) {
                String orderId = result.get("orderId");
                String orderToken = result.get("orderToken");
                if (orderId != null && orderToken != null) {
                    cashfreeManager.startPayment(orderId, orderToken, amount);
                } else {
                    Toast.makeText(WalletActivity.this, "Failed to create order", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(WalletActivity.this, "Error creating order: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onPaymentSuccess(String orderId, String transactionId) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "Payment Successful! Transaction ID: " + transactionId, Toast.LENGTH_LONG).show();
        loadWalletData(); // Refresh wallet data
    }

    @Override
    public void onPaymentFailure(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "Payment Failed: " + errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPaymentError(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "Payment Error: " + errorMessage, Toast.LENGTH_LONG).show();
    }
}
