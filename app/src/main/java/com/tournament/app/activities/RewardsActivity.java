package com.tournament.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tournament.app.R;
import com.tournament.app.adapters.RewardAdapter;
import com.tournament.app.api.ApiRepository;
import com.tournament.app.model.RewardModel;
import com.tournament.app.utils.SessionManager;

import java.util.List;
import java.util.Map;

public class RewardsActivity extends AppCompatActivity {

    private TextView referralCodeTextView, totalReferralsTextView, referralProgressTextView;
    private Button claimDailyRewardButton;
    private RecyclerView rewardHistoryRecyclerView;
    private ProgressBar progressBar;

    private ApiRepository apiRepository;
    private SessionManager sessionManager;
    private RewardAdapter rewardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        referralCodeTextView = findViewById(R.id.referralCodeTextView);
        totalReferralsTextView = findViewById(R.id.totalReferralsTextView);
        referralProgressTextView = findViewById(R.id.referralProgressTextView);
        claimDailyRewardButton = findViewById(R.id.claimDailyRewardButton);
        rewardHistoryRecyclerView = findViewById(R.id.rewardHistoryRecyclerView);
        progressBar = findViewById(R.id.progressBar);

        apiRepository = new ApiRepository();
        sessionManager = new SessionManager(this);

        rewardHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rewardAdapter = new RewardAdapter(this);
        rewardHistoryRecyclerView.setAdapter(rewardAdapter);

        claimDailyRewardButton.setOnClickListener(v -> {
            claimDailyReward();
        });

        loadRewardsData();
    }

    private void loadRewardsData() {
        String firebaseUid = sessionManager.getFirebaseUid();
        if (firebaseUid == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        // Load referral data (assuming API or Firestore provides this)
        referralCodeTextView.setText("Referral Code: XYZ123"); // Placeholder
        totalReferralsTextView.setText("Total Referrals: 5"); // Placeholder
        referralProgressTextView.setText("Referral Progress: 2/10"); // Placeholder

        apiRepository.getRewardHistory(firebaseUid, new ApiRepository.ApiCallback<List<RewardModel>>() {
            @Override
            public void onSuccess(List<RewardModel> result) {
                rewardAdapter.setRewardList(result);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(RewardsActivity.this, "Failed to load reward history: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void claimDailyReward() {
        String firebaseUid = sessionManager.getFirebaseUid();
        if (firebaseUid == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        apiRepository.claimDailyReward(firebaseUid, new ApiRepository.ApiCallback<Map<String, String>>() {
            @Override
            public void onSuccess(Map<String, String> result) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RewardsActivity.this, "Daily reward claimed!", Toast.LENGTH_SHORT).show();
                loadRewardsData(); // Refresh data
            }

            @Override
            public void onFailure(Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RewardsActivity.this, "Failed to claim daily reward: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
