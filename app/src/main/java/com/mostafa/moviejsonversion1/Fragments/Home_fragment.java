package com.mostafa.moviejsonversion1.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;
import com.mostafa.moviejsonversion1.Adapters.BannerMovieAdapter;
import com.mostafa.moviejsonversion1.Adapters.MovieRecyclerViewAdapter;
import com.mostafa.moviejsonversion1.Adapters.OnMoviesListener;
import com.mostafa.moviejsonversion1.Activity.Description_movie;
import com.mostafa.moviejsonversion1.Models.MovieModel;
import com.mostafa.moviejsonversion1.R;
import com.mostafa.moviejsonversion1.Request.ServiceY;
import com.mostafa.moviejsonversion1.Response.MovieSearchResponse;
import com.mostafa.moviejsonversion1.Utils.Constants;
import com.mostafa.moviejsonversion1.Utils.MovieApi;
import com.mostafa.moviejsonversion1.ViewModels.MovieListViewModel;

import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_fragment extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    private BannerMovieAdapter bannerMovieAdapter;
    private MovieListViewModel movieListViewModelPop,viewModelUpcoming,viewModelTopRated,viewModelNowPlaying;
    private MovieRecyclerViewAdapter movieRecyclerViewAdapterPop,recyclerViewAdapterUpcoming,movieRecyclerViewAdapterTop,movieRecyclerViewAdapterNow;
    List<MovieModel> modelList,modelListPop,bannerList;
    ViewPager viewPager;
    TabLayout tabLayout;
    RecyclerView recyclerViewPop,recyclerViewUpcoming,recyclerViewTop ,recyclerViewNow;
    ShimmerFrameLayout shimmerFrameLayoutUp,shimmerFrameLayoutPop,shimmerFrameLayoutBanner,shimmerFrameLayoutTop,shimmerFrameLayoutNow;
    int d =1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_frgment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        swipeRefreshLayout = getActivity().findViewById(R.id.swipeRefresh);
        viewPager = getActivity().findViewById(R.id.view_pager);
        tabLayout = getActivity().findViewById(R.id.tab_banner);

        shimmerFrameLayoutUp = getActivity().findViewById(R.id.shimmer_layout_up);
        shimmerFrameLayoutPop = getActivity().findViewById(R.id.shimmer_layout_pop);
        shimmerFrameLayoutBanner =getActivity().findViewById(R.id.shimmer_layout_banner);
        shimmerFrameLayoutTop = getActivity().findViewById(R.id.shimmer_layout_Top);
        shimmerFrameLayoutNow = getActivity().findViewById(R.id.shimmer_layout_Now);

        recyclerViewPop = getActivity().findViewById(R.id.recyclerPop);
        recyclerViewUpcoming = getActivity().findViewById(R.id.recyclerUp);
        recyclerViewTop = getActivity().findViewById(R.id.recyclerTop);
        recyclerViewNow = getActivity().findViewById(R.id.recyclerNow);

        modelList = new ArrayList<>();
        bannerList =new ArrayList<>();
        modelListPop = new ArrayList<>();


        movieListViewModelPop = new ViewModelProvider(this).get(MovieListViewModel.class);
        viewModelUpcoming = new ViewModelProvider(this).get(MovieListViewModel.class);
        viewModelTopRated = new ViewModelProvider(this).get(MovieListViewModel.class);
        viewModelNowPlaying = new ViewModelProvider(this).get(MovieListViewModel.class);

        movieRecyclerViewAdapterPop = new MovieRecyclerViewAdapter();
        recyclerViewAdapterUpcoming = new MovieRecyclerViewAdapter();
        movieRecyclerViewAdapterTop = new MovieRecyclerViewAdapter();
        movieRecyclerViewAdapterNow = new MovieRecyclerViewAdapter();




        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shimmerFrameLayoutPop.setVisibility(View.VISIBLE);
                recyclerViewPop.setVisibility(View.GONE);
                shimmerFrameLayoutPop.startShimmer();
                movieListViewModelPop.searchMoviePopular(1);
                putData(recyclerViewPop,movieRecyclerViewAdapterPop);
                observePopChange();


                shimmerFrameLayoutUp.setVisibility(View.VISIBLE);
                recyclerViewUpcoming.setVisibility(View.GONE);
                shimmerFrameLayoutUp.startShimmer();
                viewModelUpcoming.searchMovieUpcoming(1);
                putData(recyclerViewUpcoming,recyclerViewAdapterUpcoming);
                observeUpcomingChange();
/*
                shimmerFrameLayoutBanner.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.GONE);
                tabLayout.setVisibility(View.GONE);
                shimmerFrameLayoutBanner.startShimmer();

                shimmerFrameLayoutTop.setVisibility(View.VISIBLE);
                recyclerViewTop.setVisibility(View.GONE);
                shimmerFrameLayoutTop.startShimmer();
                viewModelTopRated.searchMovieTopRated(1);
                putData(recyclerViewTop,movieRecyclerViewAdapterTop);
                observeTopRatedChange();

 */

                shimmerFrameLayoutNow.setVisibility(View.VISIBLE);
                recyclerViewNow.setVisibility(View.GONE);
                shimmerFrameLayoutNow.startShimmer();
                viewModelNowPlaying.searchMovieNowPlaying(1);
                putData(recyclerViewNow,movieRecyclerViewAdapterNow);
                observeNowPlayingChange();


                swipeRefreshLayout.setRefreshing(false);
            }
        });




        shimmerFrameLayoutPop.startShimmer();
        shimmerFrameLayoutBanner.startShimmer();
        movieListViewModelPop.searchMoviePopular(1);

        putData(recyclerViewPop,movieRecyclerViewAdapterPop);
        movieRecyclerViewAdapterPop.setOnMoviesListener(new OnMoviesListener() {
            @Override
            public void onMovieClick(int position) {
                startDescription(position,movieRecyclerViewAdapterPop);
            }

            @Override
            public void onCategoryClick(String category) {

            }
        });
        recyclerViewPop.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if( !recyclerView.canScrollHorizontally(1)){
                    movieListViewModelPop.searchNextPopular();
                }
            }
        });
        observePopChange();
        //searchResponse();


        ////////////////////////////////////






        //upcoming movies
        shimmerFrameLayoutUp.startShimmer();
        viewModelUpcoming.searchMovieUpcoming(1);
        putData(recyclerViewUpcoming,recyclerViewAdapterUpcoming);
        recyclerViewAdapterUpcoming.setOnMoviesListener(new OnMoviesListener() {
            @Override
            public void onMovieClick(int position) {
                startDescription(position,recyclerViewAdapterUpcoming);
            }

            @Override
            public void onCategoryClick(String category) {

            }
        });
        recyclerViewUpcoming.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if( !recyclerView.canScrollHorizontally(1)){
                    viewModelUpcoming.searchNextUpcoming();
                }
            }
        });
        observeUpcomingChange();

        //top Rated movies
        shimmerFrameLayoutTop.startShimmer();
        viewModelTopRated.searchMovieTopRated(1);
        putData(recyclerViewTop,movieRecyclerViewAdapterTop);
        movieRecyclerViewAdapterTop.setOnMoviesListener(new OnMoviesListener() {
            @Override
            public void onMovieClick(int position) {
                startDescription(position,movieRecyclerViewAdapterTop);
            }

            @Override
            public void onCategoryClick(String category) {

            }
        });
        recyclerViewTop.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if( !recyclerView.canScrollHorizontally(1)){
                    viewModelTopRated.searchNextTopRated();
                }
            }
        });
        observeTopRatedChange();

        //Now playing movies
        shimmerFrameLayoutNow.startShimmer();
        viewModelNowPlaying.searchMovieNowPlaying(1);
        putData(recyclerViewNow,movieRecyclerViewAdapterNow);
        movieRecyclerViewAdapterNow.setOnMoviesListener(new OnMoviesListener() {
            @Override
            public void onMovieClick(int position) {
                startDescription(position,movieRecyclerViewAdapterNow);
            }

            @Override
            public void onCategoryClick(String category) {

            }
        });
        recyclerViewNow.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if( !recyclerView.canScrollHorizontally(1)){
                    viewModelNowPlaying.searchNextNowPlaying();
                }
            }
        });
        observeNowPlayingChange();


    }
    private void setBannerData(){
        bannerMovieAdapter = new BannerMovieAdapter(getContext(),bannerList);
        bannerMovieAdapter.notifyDataSetChanged();
        viewPager.setAdapter(bannerMovieAdapter);
        Timer slideTimer = new Timer();
        slideTimer.scheduleAtFixedRate(new autoSlide() ,3000,5000);
        tabLayout.setupWithViewPager(viewPager,true);
        Objects.requireNonNull(viewPager.getAdapter()).notifyDataSetChanged();
    }



    private void observePopChange() {
        movieListViewModelPop.getMoviesPop().observe(getActivity(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels != null) {


                    recyclerViewPop.setVisibility(View.VISIBLE);
                    shimmerFrameLayoutPop.stopShimmer();
                    shimmerFrameLayoutPop.setVisibility(View.GONE);
                    movieRecyclerViewAdapterPop.setmMovies(movieModels);
                    movieRecyclerViewAdapterPop.notifyDataSetChanged();




                }

            }
        });
    }
    private void observeUpcomingChange() {
        viewModelUpcoming.getMoviesUpcoming().observe(getActivity(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels != null) {

                    recyclerViewUpcoming.setVisibility(View.VISIBLE);
                    shimmerFrameLayoutUp.stopShimmer();
                    shimmerFrameLayoutUp.setVisibility(View.GONE);
                    recyclerViewAdapterUpcoming.setmMovies(movieModels);
                    recyclerViewAdapterUpcoming.notifyDataSetChanged();

                }

            }
        });
    }
    private void observeTopRatedChange() {
            viewModelTopRated.getMoviesTopRated().observe(getActivity(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels != null) {

                    recyclerViewTop.setVisibility(View.VISIBLE);
                    shimmerFrameLayoutTop.stopShimmer();
                    shimmerFrameLayoutTop.setVisibility(View.GONE);
                    movieRecyclerViewAdapterTop.setmMovies(movieModels);
                    movieRecyclerViewAdapterTop.notifyDataSetChanged();

                    viewPager.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.VISIBLE);
                    shimmerFrameLayoutBanner.stopShimmer();
                    shimmerFrameLayoutBanner.setVisibility(View.GONE);
                    for(MovieModel model : movieModels){
                        if(d < 11){
                            bannerList.add(new MovieModel("","","",model.getBackdrop_path(),"",0,0));
                            d++;
                        }
                    }
                    tabLayout.setBackground(Drawable.createFromPath(Constants.IMAGE_URL + bannerList.get(0).getBackdrop_path()));
                    setBannerData();

                }

            }
        });
    }
    private void observeNowPlayingChange() {
         viewModelNowPlaying.getMoviesNowPlaying().observe(getActivity(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels != null) {

                    recyclerViewNow.setVisibility(View.VISIBLE);
                    shimmerFrameLayoutNow.stopShimmer();
                    shimmerFrameLayoutNow.setVisibility(View.GONE);
                    movieRecyclerViewAdapterNow.setmMovies(movieModels);
                    movieRecyclerViewAdapterNow.notifyDataSetChanged();

                }

            }
        });
    }



    private void searchResponse() {
        MovieApi movieApi= ServiceY.getMovieApi();

        Call<MovieSearchResponse> responseCall = movieApi.searchMovie(
                Constants.API_KEY,
                "Luca",
                1
        );

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if(response.code()==200)
                {
                }
            }

            @Override
            public void onFailure(@NotNull Call<MovieSearchResponse> call, @NotNull Throwable t) {

            }
        });
    }
    private void searchResponseById(){
        MovieApi movieApi = ServiceY.getMovieApi();
        Call<MovieModel> responseCall = movieApi.getMovie(
                555,
                Constants.API_KEY
        );

        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if(response.code()==200)
                {
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }



    private void putData(RecyclerView recyclerView,MovieRecyclerViewAdapter movieRecyclerViewAdapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(movieRecyclerViewAdapter);


    }
    public class autoSlide extends TimerTask{

        @Override
        public void run() {
            if(getActivity() == null)
                return;
            else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int size = 6;
                        if (viewPager.getCurrentItem() < bannerList.size() - 1) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                            viewPager.getAdapter().notifyDataSetChanged();
                            try {
                                URL url = new URL(Constants.BASE_URL + bannerList.get(viewPager.getCurrentItem()).getBackdrop_path());
                                Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                Drawable image = new BitmapDrawable(getActivity().getResources(), bitmap);

                                tabLayout.setBackground(image);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            viewPager.setCurrentItem(0);
                            viewPager.getAdapter().notifyDataSetChanged();
                            if (d == 1) {
                                tabLayout.setBackground(Drawable.createFromPath(Constants.IMAGE_URL + bannerList.get(0).getBackdrop_path()));

                            }

                        }
                    }
                });
            }
        }
    }

    private void startDescription(int position,MovieRecyclerViewAdapter movieRecyclerViewAdapter) {
        Intent intent = new Intent(getContext(), Description_movie.class);
        intent.putExtra("movie",movieRecyclerViewAdapter.getSelectedMovieId(position));
        startActivity(intent);
    }

}