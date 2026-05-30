package com.tournament.app.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.Locale;

public class CountdownManager {

    private CountDownTimer countDownTimer;
    private TextView countdownTextView;
    private long timeLeftInMillis;

    public CountdownManager(TextView textView, long totalTimeInMillis) {
        countdownTextView = textView;
        timeLeftInMillis = totalTimeInMillis;
    }

    public void startCountdown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                countdownTextView.setText("00:00:00");
                // Optionally, trigger an event when countdown finishes
            }
        }.start();
    }

    public void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void updateCountdownText() {
        int hours = (int) (timeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((timeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        countdownTextView.setText(timeLeftFormatted);
    }

    public long getTimeLeftInMillis() {
        return timeLeftInMillis;
    }
}
