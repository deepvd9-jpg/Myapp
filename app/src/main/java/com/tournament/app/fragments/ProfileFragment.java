package com.tournament.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.tournament.app.R;
import com.tournament.app.api.ApiRepository;
import com.tournament.app.model.UserModel;
import com.tournament.app.utils.SessionManager;
import com.tournament.app.utils.XPLevelManager;

public class ProfileFragment extends Fragment {

    private ImageView profileAvatarImageView;
    private TextView usernameTextView, levelTextView, matchesPlayedTextView, totalKillsTextView, vipStatusTextView;
    private ProgressBar xpProgressBar;

    private ApiRepository apiRepository;
    private SessionManager sessionManager;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileAvatarImageView = view.findViewById(R.id.profileAvatarImageView);
        usernameTextView = view.findViewById(R.id.usernameTextView);
        levelTextView = view.findViewById(R.id.levelTextView);
        matchesPlayedTextView = view.findViewById(R.id.matchesPlayedTextView);
        totalKillsTextView = view.findViewById(R.id.totalKillsTextView);
        vipStatusTextView = view.findViewById(R.id.vipStatusTextView);
        xpProgressBar = view.findViewById(R.id.xpProgressBar);

        apiRepository = new ApiRepository();
        sessionManager = new SessionManager(getContext());

        loadUserProfile();

        return view;
    }

    private void loadUserProfile() {
        String firebaseUid = sessionManager.getFirebaseUid();
        if (firebaseUid == null) {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        apiRepository.getUserProfile(firebaseUid, new ApiRepository.ApiCallback<UserModel>() {
            @Override
            public void onSuccess(UserModel user) {
                if (user != null) {
                    usernameTextView.setText(user.getUsername());
                    levelTextView.setText("Level: " + XPLevelManager.getLevel(user.getTotalXP()));
                    matchesPlayedTextView.setText("Matches Played: " + user.getMatchesPlayed());
                    totalKillsTextView.setText("Total Kills: " + user.getTotalKills());
                    vipStatusTextView.setText("VIP Status: " + (user.isVipStatus() ? "Active" : "Inactive"));

                    xpProgressBar.setMax(XPLevelManager.getXPForNextLevel(XPLevelManager.getLevel(user.getTotalXP())));
                    xpProgressBar.setProgress(XPLevelManager.getProgressToNextLevel(user.getTotalXP()));

                    if (user.getProfileAvatarUrl() != null && !user.getProfileAvatarUrl().isEmpty()) {
                        Glide.with(getContext()).load(user.getProfileAvatarUrl()).into(profileAvatarImageView);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), "Failed to load profile: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
