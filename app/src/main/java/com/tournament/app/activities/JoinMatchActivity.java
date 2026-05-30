package com.tournament.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tournament.app.R;
import com.tournament.app.api.ApiRepository;
import com.tournament.app.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JoinMatchActivity extends AppCompatActivity {

    private RadioGroup modeRadioGroup;
    private LinearLayout gameUidInputContainer;
    private Button joinMatchButton;
    private ProgressBar progressBar;

    private ApiRepository apiRepository;
    private SessionManager sessionManager;
    private String matchId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_match);

        modeRadioGroup = findViewById(R.id.modeRadioGroup);
        gameUidInputContainer = findViewById(R.id.gameUidInputContainer);
        joinMatchButton = findViewById(R.id.joinMatchButton);
        progressBar = findViewById(R.id.progressBar);

        apiRepository = new ApiRepository();
        sessionManager = new SessionManager(this);

        matchId = getIntent().getStringExtra("MATCH_ID");

        modeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            gameUidInputContainer.removeAllViews();
            int numInputs = 0;
            if (checkedId == R.id.radioSolo) {
                numInputs = 1;
            } else if (checkedId == R.id.radioDuo) {
                numInputs = 2;
            } else if (checkedId == R.id.radioSquad) {
                numInputs = 4;
            }
            addGameUidInputs(numInputs);
        });

        // Set default to Solo
        modeRadioGroup.check(R.id.radioSolo);

        joinMatchButton.setOnClickListener(v -> {
            joinMatch();
        });
    }

    private void addGameUidInputs(int count) {
        for (int i = 0; i < count; i++) {
            EditText editText = new EditText(this);
            editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            editText.setHint("Game UID " + (i + 1));
            editText.setId(View.generateViewId()); // Generate unique ID for each EditText
            gameUidInputContainer.addView(editText);
        }
    }

    private void joinMatch() {
        String firebaseUid = sessionManager.getFirebaseUid();
        if (firebaseUid == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> gameUIDs = new ArrayList<>();
        for (int i = 0; i < gameUidInputContainer.getChildCount(); i++) {
            EditText editText = (EditText) gameUidInputContainer.getChildAt(i);
            String gameUid = editText.getText().toString().trim();
            if (gameUid.isEmpty()) {
                Toast.makeText(this, "Please enter all Game UIDs", Toast.LENGTH_SHORT).show();
                return;
            }
            gameUIDs.add(gameUid);
        }

        progressBar.setVisibility(View.VISIBLE);
        apiRepository.joinMatch(firebaseUid, matchId, gameUIDs, new ApiRepository.ApiCallback<Map<String, String>>() {
            @Override
            public void onSuccess(Map<String, String> result) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(JoinMatchActivity.this, "Successfully joined match!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(JoinMatchActivity.this, "Failed to join match: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
