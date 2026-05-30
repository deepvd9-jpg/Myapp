package com.tournament.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tournament.app.R;
import com.tournament.app.activities.WalletActivity;
import com.tournament.app.adapters.WalletAdapter;
import com.tournament.app.api.ApiRepository;
import com.tournament.app.model.WalletModel;
import com.tournament.app.utils.SessionManager;

import java.util.List;
import java.util.Map;

public class WalletFragment extends Fragment {

    private TextView balanceTextView;
    private Button addMoneyButton;
    private RecyclerView transactionHistoryRecyclerView;
    private ProgressBar progressBar;

    private ApiRepository apiRepository;
    private SessionManager sessionManager;
    private WalletAdapter walletAdapter;

    public WalletFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        balanceTextView = view.findViewById(R.id.balanceTextView);
        addMoneyButton = view.findViewById(R.id.addMoneyButton);
        transactionHistoryRecyclerView = view.findViewById(R.id.transactionHistoryRecyclerView);
        progressBar = view.findViewById(R.id.progressBar);

        apiRepository = new ApiRepository();
        sessionManager = new SessionManager(getContext());

        transactionHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        walletAdapter = new WalletAdapter(getContext());
        transactionHistoryRecyclerView.setAdapter(walletAdapter);

        addMoneyButton.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), WalletActivity.class));
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadWalletData();
    }

    private void loadWalletData() {
        String firebaseUid = sessionManager.getFirebaseUid();
        if (firebaseUid == null) {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Failed to load balance: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Failed to load history: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
