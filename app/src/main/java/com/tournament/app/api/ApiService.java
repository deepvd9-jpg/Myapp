package com.tournament.app.api;

import com.tournament.app.model.MatchModel;
import com.tournament.app.model.RewardModel;
import com.tournament.app.model.UserModel;
import com.tournament.app.model.WalletModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    // Auth
    @POST("auth/login")
    Call<Map<String, String>> login(@Body Map<String, String> credentials);

    // Match
    @POST("match/join")
    Call<Map<String, String>> joinMatch(@Body Map<String, Object> matchDetails);

    // Rewards
    @POST("rewards/daily")
    Call<Map<String, String>> claimDailyReward(@Body Map<String, String> userId);

    // Wallet
    @POST("wallet/createOrder")
    Call<Map<String, String>> createWalletOrder(@Body Map<String, Object> orderDetails);

    // Profile Update (example, as per instruction, frontend should not write directly)
    // @POST("profile/update")
    // Call<Map<String, String>> updateProfile(@Body UserModel userModel);

    // Example GET requests (assuming backend provides these)
    @GET("user/{uid}")
    Call<UserModel> getUserProfile(@Path("uid") String uid);

    @GET("matches/upcoming")
    Call<List<MatchModel>> getUpcomingMatches();

    @GET("matches/playing")
    Call<List<MatchModel>> getPlayingMatches();

    @GET("matches/ended")
    Call<List<MatchModel>> getEndedMatches();

    @GET("wallet/history/{uid}")
    Call<List<WalletModel>> getWalletHistory(@Path("uid") String uid);

    @GET("rewards/available/{uid}")
    Call<List<RewardModel>> getAvailableRewards(@Path("uid") String uid);

    @GET("rewards/history/{uid}")
    Call<List<RewardModel>> getRewardHistory(@Path("uid") String uid);

    @GET("wallet/balance/{uid}")
    Call<Map<String, Double>> getWalletBalance(@Path("uid") String uid);

}
