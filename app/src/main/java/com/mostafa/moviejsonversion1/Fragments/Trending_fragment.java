package com.mostafa.moviejsonversion1.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mostafa.moviejsonversion1.Activity.Description_movie;
import com.mostafa.moviejsonversion1.Adapters.OnMoviesListener;
import com.mostafa.moviejsonversion1.Adapters.TrendingRecyclerViewAdapter;
import com.mostafa.moviejsonversion1.Description_Trend;
import com.mostafa.moviejsonversion1.Models.TrendingModel;
import com.mostafa.moviejsonversion1.R;
import com.mostafa.moviejsonversion1.Request.ServiceY;
import com.mostafa.moviejsonversion1.Response.TrendingSearchResponse;
import com.mostafa.moviejsonversion1.Utils.Constants;
import com.mostafa.moviejsonversion1.Utils.MovieApi;
import com.mostafa.moviejsonversion1.ViewModels.MovieListViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Trending_fragment extends Fragment{

    private TrendingRecyclerViewAdapter trendingRecyclerViewAdapter;
    private MovieListViewModel movieListViewModel;
    SwipeRefreshLayout swipeRefreshLayout;
    ShimmerFrameLayout shimmerFrameLayout;
    RecyclerView recyclerView;
    ImageButton setting;
    String target = "tv", time ="day";
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getActivity().findViewById(R.id.recyclerTrend);
        swipeRefreshLayout = getActivity().findViewById(R.id.swipeRefreshTrend);
        shimmerFrameLayout = getActivity().findViewById(R.id.shimmer_layout_trend);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.setVisibility(View.GONE);
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                shimmerFrameLayout.startShimmer();
                movieListViewModel.searchTrending(1, target, time);
                putData(recyclerView,trendingRecyclerViewAdapter);
                observeChange();
                swipeRefreshLayout.setRefreshing(false);
            }
        });




        trendingRecyclerViewAdapter = new TrendingRecyclerViewAdapter();
        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        //searchResponse();
        shimmerFrameLayout.startShimmer();
        movieListViewModel.searchTrending(1, "tv" , "day");
        putData(recyclerView,trendingRecyclerViewAdapter);
        trendingRecyclerViewAdapter.setOnMoviesListener(new OnMoviesListener() {
            @Override
            public void onMovieClick(int position) {
               startDescription(position,trendingRecyclerViewAdapter);
            }

            @Override
            public void onCategoryClick(String category) {

            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if( !recyclerView.canScrollHorizontally(1)){
                    movieListViewModel.searchNextTrending();
                }
            }
        });
        observeChange();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trending_fragment, container, false);

    }
    private void putData(RecyclerView recyclerView, TrendingRecyclerViewAdapter movieRecyclerViewAdapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(movieRecyclerViewAdapter);
    }

    private void observeChange() {
        movieListViewModel.getTrending().observe(getActivity(), new Observer<List<TrendingModel>>() {
            @Override
            public void onChanged(List<TrendingModel> trendingModels) {
                if(trendingModels != null) {
                    recyclerView.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    trendingRecyclerViewAdapter.setmTrend(trendingModels);
                    trendingRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    private void searchResponse() {
        MovieApi movieApi= ServiceY.getMovieApi();

        Call<TrendingSearchResponse> responseCall =movieApi.getTrending(

                "tv",
                "day",
                Constants.API_KEY,
                1
        );

        responseCall.enqueue(new Callback<TrendingSearchResponse>() {
            @Override
            public void onResponse(Call<TrendingSearchResponse> call, Response<TrendingSearchResponse> response) {
                if(response.code()==200)
                {
                    Log.v("response" , response.body().toString());
                }
            }

            @Override
            public void onFailure(@NotNull Call<TrendingSearchResponse> call, @NotNull Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startDescription(int position, TrendingRecyclerViewAdapter movieRecyclerViewAdapter) {
        Intent intent = new Intent(getContext(), Description_Trend.class);
        intent.putExtra("movie",movieRecyclerViewAdapter.getSelectedTrendingId(position));
        startActivity(intent);
    }
}