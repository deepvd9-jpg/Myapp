package com.tournament.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.tournament.app.R;
import com.tournament.app.api.ApiRepository;
import com.tournament.app.model.UserModel;
import com.tournament.app.utils.SessionManager;
import com.tournament.app.utils.XPLevelManager;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profileAvatarImageView;
    private TextView usernameTextView, levelTextView, matchesPlayedTextView, totalKillsTextView, vipStatusTextView;
    private ProgressBar xpProgressBar;

    private ApiRepository apiRepository;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileAvatarImageView = findViewById(R.id.profileAvatarImageView);
        usernameTextView = findViewById(R.id.usernameTextView);
        levelTextView = findViewById(R.id.levelTextView);
        matchesPlayedTextView = findViewById(R.id.matchesPlayedTextView);
        totalKillsTextView = findViewById(R.id.totalKillsTextView);
        vipStatusTextView = findViewById(R.id.vipStatusTextView);
        xpProgressBar = findViewById(R.id.xpProgressBar);

        apiRepository = new ApiRepository();
        sessionManager = new SessionManager(this);

        loadUserProfile();
    }

    private void loadUserProfile() {
        String firebaseUid = sessionManager.getFirebaseUid();
        if (firebaseUid == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
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
                        Glide.with(ProfileActivity.this).load(user.getProfileAvatarUrl()).into(profileAvatarImageView);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(ProfileActivity.this, "Failed to load profile: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
