package com.tournament.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tournament.app.R;
import com.tournament.app.activities.RoomActivity;
import com.tournament.app.adapters.MatchAdapter;
import com.tournament.app.api.ApiRepository;
import com.tournament.app.model.MatchModel;

import java.util.List;

public class PlayingFragment extends Fragment implements MatchAdapter.OnMatchClickListener {

    private RecyclerView playingMatchesRecyclerView;
    private ProgressBar progressBar;
    private MatchAdapter matchAdapter;
    private ApiRepository apiRepository;

    public PlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playing, container, false);

        playingMatchesRecyclerView = view.findViewById(R.id.playingMatchesRecyclerView);
        progressBar = view.findViewById(R.id.progressBar);

        apiRepository = new ApiRepository();

        playingMatchesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        matchAdapter = new MatchAdapter(getContext(), this);
        playingMatchesRecyclerView.setAdapter(matchAdapter);

        loadPlayingMatches();

        return view;
    }

    private void loadPlayingMatches() {
        progressBar.setVisibility(View.VISIBLE);
        apiRepository.getPlayingMatches(new ApiRepository.ApiCallback<List<MatchModel>>() {
            @Override
            public void onSuccess(List<MatchModel> result) {
                matchAdapter.setMatchList(result);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), "Failed to load playing matches: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onMatchClick(MatchModel match) {
        Intent intent = new Intent(getContext(), RoomActivity.class);
        intent.putExtra("MATCH_DATA", match);
        startActivity(intent);
    }
}
