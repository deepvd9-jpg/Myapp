package com.tournament.app.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tournament.app.R;
import com.tournament.app.model.MatchModel;
import com.tournament.app.utils.CountdownManager;

public class RoomActivity extends AppCompatActivity {

    private TextView mapNameTextView, countdownTextView, roomIdTextView, passwordTextView, matchSummaryTextView, gameUidsTextView, resultStatusTextView, prizeTextView, xpTextView;
    private Button supportButton;

    private MatchModel currentMatch;
    private CountdownManager countdownManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        mapNameTextView = findViewById(R.id.mapNameTextView);
        countdownTextView = findViewById(R.id.countdownTextView);
        roomIdTextView = findViewById(R.id.roomIdTextView);
        passwordTextView = findViewById(R.id.passwordTextView);
        matchSummaryTextView = findViewById(R.id.matchSummaryTextView);
        gameUidsTextView = findViewById(R.id.gameUidsTextView);
        resultStatusTextView = findViewById(R.id.resultStatusTextView);
        prizeTextView = findViewById(R.id.prizeTextView);
        xpTextView = findViewById(R.id.xpTextView);
        supportButton = findViewById(R.id.supportButton);

        // Get match data from intent
        currentMatch = (MatchModel) getIntent().getSerializableExtra("MATCH_DATA");

        if (currentMatch != null) {
            updateUI(currentMatch);
        }

        supportButton.setOnClickListener(v -> {
            // Open Telegram URL
            Intent telegramIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/your_support_channel"));
            startActivity(telegramIntent);
        });
    }

    private void updateUI(MatchModel match) {
        mapNameTextView.setText(match.getMapName());

        // Handle match states
        switch (match.getMatchState()) {
            case "upcoming":
                roomIdTextView.setVisibility(View.GONE);
                passwordTextView.setVisibility(View.GONE);
                matchSummaryTextView.setVisibility(View.GONE);
                gameUidsTextView.setVisibility(View.GONE);
                resultStatusTextView.setVisibility(View.GONE);
                prizeTextView.setVisibility(View.GONE);
                xpTextView.setVisibility(View.GONE);

                countdownTextView.setVisibility(View.VISIBLE);
                // Start countdown
                countdownManager = new CountdownManager(countdownTextView, match.getStartTime() - System.currentTimeMillis());
                countdownManager.startCountdown();
                break;
            case "playing":
                countdownTextView.setVisibility(View.GONE);
                matchSummaryTextView.setVisibility(View.GONE);
                gameUidsTextView.setVisibility(View.GONE);
                resultStatusTextView.setVisibility(View.GONE);
                prizeTextView.setVisibility(View.GONE);
                xpTextView.setVisibility(View.GONE);

                roomIdTextView.setVisibility(View.VISIBLE);
                passwordTextView.setVisibility(View.VISIBLE);
                roomIdTextView.setText("Room ID: " + match.getRoomId());
                passwordTextView.setText("Password: " + match.getPassword());
                break;
            case "ended":
                countdownTextView.setVisibility(View.GONE);
                roomIdTextView.setVisibility(View.GONE);
                passwordTextView.setVisibility(View.GONE);

                matchSummaryTextView.setVisibility(View.VISIBLE);
                gameUidsTextView.setVisibility(View.VISIBLE);
                resultStatusTextView.setVisibility(View.VISIBLE);
                prizeTextView.setVisibility(View.VISIBLE);
                xpTextView.setVisibility(View.VISIBLE);

                matchSummaryTextView.setText("Match Summary: ..."); // Populate with actual summary
                gameUidsTextView.setText("Your Game UIDs: " + (match.getGameUIDs() != null ? String.join(", ", match.getGameUIDs()) : "N/A"));
                resultStatusTextView.setText("Result: " + match.getResultStatus());
                prizeTextView.setText("Prize: " + match.getPrizeDistributed());
                xpTextView.setText("XP: " + match.getXpDistributed());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countdownManager != null) {
            countdownManager.stopCountdown();
        }
    }
}
