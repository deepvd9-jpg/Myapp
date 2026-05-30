package com.tournament.app.fragments;

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
import com.tournament.app.adapters.RewardAdapter;
import com.tournament.app.api.ApiRepository;
import com.tournament.app.model.RewardModel;
import com.tournament.app.utils.SessionManager;

import java.util.List;
import java.util.Map;

public class RewardsFragment extends Fragment {

    private TextView referralCodeTextView, totalReferralsTextView, referralProgressTextView;
    private Button claimDailyRewardButton;
    private RecyclerView rewardHistoryRecyclerView;
    private ProgressBar progressBar;

    private ApiRepository apiRepository;
    private SessionManager sessionManager;
    private RewardAdapter rewardAdapter;

    public RewardsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rewards, container, false);

        referralCodeTextView = view.findViewById(R.id.referralCodeTextView);
        totalReferralsTextView = view.findViewById(R.id.totalReferralsTextView);
        referralProgressTextView = view.findViewById(R.id.referralProgressTextView);
        claimDailyRewardButton = view.findViewById(R.id.claimDailyRewardButton);
        rewardHistoryRecyclerView = view.findViewById(R.id.rewardHistoryRecyclerView);
        progressBar = view.findViewById(R.id.progressBar);

        apiRepository = new ApiRepository();
        sessionManager = new SessionManager(getContext());

        rewardHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rewardAdapter = new RewardAdapter(getContext());
        rewardHistoryRecyclerView.setAdapter(rewardAdapter);

        claimDailyRewardButton.setOnClickListener(v -> {
            claimDailyReward();
        });

        loadRewardsData();

        return view;
    }

    private void loadRewardsData() {
        String firebaseUid = sessionManager.getFirebaseUid();
        if (firebaseUid == null) {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Failed to load reward history: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void claimDailyReward() {
        String firebaseUid = sessionManager.getFirebaseUid();
        if (firebaseUid == null) {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        apiRepository.claimDailyReward(firebaseUid, new ApiRepository.ApiCallback<Map<String, String>>() {
            @Override
            public void onSuccess(Map<String, String> result) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Daily reward claimed!", Toast.LENGTH_SHORT).show();
                loadRewardsData(); // Refresh data
            }

            @Override
            public void onFailure(Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Failed to claim daily reward: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
