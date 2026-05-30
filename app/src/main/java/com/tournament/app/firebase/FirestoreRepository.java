package com.tournament.app.firebase;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tournament.app.model.MatchModel;
import com.tournament.app.model.RewardModel;
import com.tournament.app.model.UserModel;
import com.tournament.app.model.WalletModel;

import java.util.ArrayList;
import java.util.List;

public class FirestoreRepository {

    private FirebaseFirestore db;

    public FirestoreRepository() {
        db = FirebaseFirestore.getInstance();
    }

    // Read User Profile
    public void getUserProfile(String uid, final FirestoreCallback<UserModel> callback) {
        db.collection("users").document(uid).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            callback.onSuccess(document.toObject(UserModel.class));
                        } else {
                            callback.onFailure(new Exception("User document not found"));
                        }
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    // Read Wallet Balance and History
    public void getWalletData(String uid, final FirestoreCallback<List<WalletModel>> callback) {
        db.collection("wallets").whereEqualTo("userId", uid).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<WalletModel> walletHistory = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            walletHistory.add(document.toObject(WalletModel.class));
                        }
                        callback.onSuccess(walletHistory);
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    // Read Match Data (Upcoming, Playing, Ended)
    public void getMatches(String state, final FirestoreCallback<List<MatchModel>> callback) {
        db.collection("matches").whereEqualTo("matchState", state).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<MatchModel> matches = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            matches.add(document.toObject(MatchModel.class));
                        }
                        callback.onSuccess(matches);
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    // Read Rewards Data
    public void getRewards(final FirestoreCallback<List<RewardModel>> callback) {
        db.collection("rewards").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<RewardModel> rewards = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            rewards.add(document.toObject(RewardModel.class));
                        }
                        callback.onSuccess(rewards);
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    public interface FirestoreCallback<T> {
        void onSuccess(T result);
        void onFailure(Exception e);
    }
}
