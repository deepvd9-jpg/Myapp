package com.tournament.app.api;

import com.tournament.app.model.MatchModel;
import com.tournament.app.model.RewardModel;
import com.tournament.app.model.UserModel;
import com.tournament.app.model.WalletModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepository {

    private ApiService apiService;

    public ApiRepository() {
        apiService = ApiClient.getApiService();
    }

    public void login(String email, String password, final ApiCallback<Map<String, String>> callback) {
        // This is a placeholder. In a real app, you'd send email/password to the backend.
        // For now, let's simulate a successful login.
        Map<String, String> credentials = new java.util.HashMap<>();
        credentials.put("email", email);
        credentials.put("password", password);

        apiService.login(credentials).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Login failed: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void joinMatch(String uid, String matchId, List<String> gameUIDs, final ApiCallback<Map<String, String>> callback) {
        Map<String, Object> matchDetails = new java.util.HashMap<>();
        matchDetails.put("uid", uid);
        matchDetails.put("matchId", matchId);
        matchDetails.put("gameUIDs", gameUIDs);

        apiService.joinMatch(matchDetails).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Join match failed: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void claimDailyReward(String userId, final ApiCallback<Map<String, String>> callback) {
        Map<String, String> userIdMap = new java.util.HashMap<>();
        userIdMap.put("userId", userId);

        apiService.claimDailyReward(userIdMap).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Claim daily reward failed: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void createWalletOrder(String userId, double amount, final ApiCallback<Map<String, String>> callback) {
        Map<String, Object> orderDetails = new java.util.HashMap<>();
        orderDetails.put("userId", userId);
        orderDetails.put("amount", amount);

        apiService.createWalletOrder(orderDetails).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Create wallet order failed: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void getUserProfile(String uid, final ApiCallback<UserModel> callback) {
        apiService.getUserProfile(uid).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Failed to get user profile: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void getUpcomingMatches(final ApiCallback<List<MatchModel>> callback) {
        apiService.getUpcomingMatches().enqueue(new Callback<List<MatchModel>>() {
            @Override
            public void onResponse(Call<List<MatchModel>> call, Response<List<MatchModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Failed to get upcoming matches: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<MatchModel>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void getPlayingMatches(final ApiCallback<List<MatchModel>> callback) {
        apiService.getPlayingMatches().enqueue(new Callback<List<MatchModel>>() {
            @Override
            public void onResponse(Call<List<MatchModel>> call, Response<List<MatchModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Failed to get playing matches: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<MatchModel>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void getEndedMatches(final ApiCallback<List<MatchModel>> callback) {
        apiService.getEndedMatches().enqueue(new Callback<List<MatchModel>>() {
            @Override
            public void onResponse(Call<List<MatchModel>> call, Response<List<MatchModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Failed to get ended matches: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<MatchModel>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void getWalletHistory(String uid, final ApiCallback<List<WalletModel>> callback) {
        apiService.getWalletHistory(uid).enqueue(new Callback<List<WalletModel>>() {
            @Override
            public void onResponse(Call<List<WalletModel>> call, Response<List<WalletModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Failed to get wallet history: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<WalletModel>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void getAvailableRewards(String uid, final ApiCallback<List<RewardModel>> callback) {
        apiService.getAvailableRewards(uid).enqueue(new Callback<List<RewardModel>>() {
            @Override
            public void onResponse(Call<List<RewardModel>> call, Response<List<RewardModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Failed to get available rewards: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<RewardModel>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void getRewardHistory(String uid, final ApiCallback<List<RewardModel>> callback) {
        apiService.getRewardHistory(uid).enqueue(new Callback<List<RewardModel>>() {
            @Override
            public void onResponse(Call<List<RewardModel>> call, Response<List<RewardModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Failed to get reward history: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<RewardModel>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void getWalletBalance(String uid, final ApiCallback<Map<String, Double>> callback) {
        apiService.getWalletBalance(uid).enqueue(new Callback<Map<String, Double>>() {
            @Override
            public void onResponse(Call<Map<String, Double>> call, Response<Map<String, Double>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Failed to get wallet balance: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<Map<String, Double>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public interface ApiCallback<T> {
        void onSuccess(T result);
        void onFailure(Throwable t);
    }
}
