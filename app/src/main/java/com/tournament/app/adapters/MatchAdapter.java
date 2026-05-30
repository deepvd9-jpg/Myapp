package com.tournament.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tournament.app.R;
import com.tournament.app.model.MatchModel;
import com.tournament.app.utils.CountdownManager;

import java.util.ArrayList;
import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {

    private Context context;
    private List<MatchModel> matchList;
    private OnMatchClickListener listener;

    public interface OnMatchClickListener {
        void onMatchClick(MatchModel match);
    }

    public MatchAdapter(Context context, OnMatchClickListener listener) {
        this.context = context;
        this.matchList = new ArrayList<>();
        this.listener = listener;
    }

    public void setMatchList(List<MatchModel> matchList) {
        this.matchList = matchList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_match, parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        MatchModel match = matchList.get(position);
        holder.bind(match, listener);
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    static class MatchViewHolder extends RecyclerView.ViewHolder {
        TextView matchNameTextView, matchTimeTextView, matchStateTextView;
        Button actionButton;
        CountdownManager countdownManager;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            matchNameTextView = itemView.findViewById(R.id.matchNameTextView);
            matchTimeTextView = itemView.findViewById(R.id.matchTimeTextView);
            matchStateTextView = itemView.findViewById(R.id.matchStateTextView);
            actionButton = itemView.findViewById(R.id.actionButton);
        }

        public void bind(MatchModel match, OnMatchClickListener listener) {
            matchNameTextView.setText(match.getMapName());
            matchStateTextView.setText(match.getMatchState());

            // Handle countdown for upcoming matches
            if (match.getMatchState().equals("upcoming")) {
                actionButton.setText("Join");
                actionButton.setVisibility(View.VISIBLE);
                actionButton.setOnClickListener(v -> listener.onMatchClick(match));

                if (countdownManager != null) {
                    countdownManager.stopCountdown();
                }
                countdownManager = new CountdownManager(matchTimeTextView, match.getStartTime() - System.currentTimeMillis());
                countdownManager.startCountdown();
            } else if (match.getMatchState().equals("playing")) {
                actionButton.setText("View Room");
                actionButton.setVisibility(View.VISIBLE);
                actionButton.setOnClickListener(v -> listener.onMatchClick(match));
                matchTimeTextView.setText("Live");
            } else if (match.getMatchState().equals("ended")) {
                actionButton.setText("View Results");
                actionButton.setVisibility(View.VISIBLE);
                actionButton.setOnClickListener(v -> listener.onMatchClick(match));
                matchTimeTextView.setText("Ended");
            }
        }
    }
}
