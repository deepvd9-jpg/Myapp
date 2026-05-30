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
import com.tournament.app.model.RewardModel;

import java.util.ArrayList;
import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.RewardViewHolder> {

    private Context context;
    private List<RewardModel> rewardList;

    public RewardAdapter(Context context) {
        this.context = context;
        this.rewardList = new ArrayList<>();
    }

    public void setRewardList(List<RewardModel> rewardList) {
        this.rewardList = rewardList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RewardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reward, parent, false);
        return new RewardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardViewHolder holder, int position) {
        RewardModel reward = rewardList.get(position);
        holder.bind(reward);
    }

    @Override
    public int getItemCount() {
        return rewardList.size();
    }

    static class RewardViewHolder extends RecyclerView.ViewHolder {
        TextView rewardNameTextView, rewardDescriptionTextView, rewardValueTextView;
        Button claimButton;

        public RewardViewHolder(@NonNull View itemView) {
            super(itemView);
            rewardNameTextView = itemView.findViewById(R.id.rewardNameTextView);
            rewardDescriptionTextView = itemView.findViewById(R.id.rewardDescriptionTextView);
            rewardValueTextView = itemView.findViewById(R.id.rewardValueTextView);
            claimButton = itemView.findViewById(R.id.claimButton);
        }

        public void bind(RewardModel reward) {
            rewardNameTextView.setText(reward.getName());
            rewardDescriptionTextView.setText(reward.getDescription());
            rewardValueTextView.setText(String.format("XP: %d, Cash: ₹%.2f", reward.getXpReward(), reward.getCashReward()));

            if (reward.isClaimed()) {
                claimButton.setText("Claimed");
                claimButton.setEnabled(false);
            } else {
                claimButton.setText("Claim");
                claimButton.setEnabled(true);
                claimButton.setOnClickListener(v -> {
                    // TODO: Implement claim reward logic
                    // This would typically involve calling an API and updating the UI
                });
            }
        }
    }
}
