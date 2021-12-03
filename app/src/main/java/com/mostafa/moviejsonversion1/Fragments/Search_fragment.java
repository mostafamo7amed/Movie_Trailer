package com.mostafa.moviejsonversion1.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mostafa.moviejsonversion1.Adapters.OnMoviesListener;
import com.mostafa.moviejsonversion1.Adapters.SearchRecyclerViewAdapter;
import com.mostafa.moviejsonversion1.Activity.Description_movie;
import com.mostafa.moviejsonversion1.Models.MovieModel;
import com.mostafa.moviejsonversion1.R;
import com.mostafa.moviejsonversion1.ViewModels.MovieListViewModel;

import java.util.List;

public class Search_fragment extends Fragment{
    private MovieListViewModel movieListViewModel;
    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;
    RecyclerView recyclerView;
    private SearchView searchView;
    Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getActivity().findViewById(R.id.recycler1);
        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        searchView = getActivity().findViewById(R.id.search_movie);
        toolbar =getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        searchMovieApi();
         putData();
        searchRecyclerViewAdapter.setOnMoviesListener(new OnMoviesListener() {
            @Override
            public void onMovieClick(int position) {
               startDescription(position);
            }

            @Override
            public void onCategoryClick(String category) {

            }
        });
        observeAnyChange();



    }

    private void searchMovieApi(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMovieApi(
                        query,
                        1
                );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                movieListViewModel.searchMovieApi(
                        newText,
                        1
                );
                return false;
            }
        });

    }
    private void observeAnyChange() {
        movieListViewModel.getMovies().observe(getActivity(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels != null) {
                    for(MovieModel model : movieModels){
                        Log.v("Tag","message"+model.getTitle());
                        searchRecyclerViewAdapter.setmMovies(movieModels);
                        searchRecyclerViewAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }
    private void putData() {
        searchRecyclerViewAdapter= new SearchRecyclerViewAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(searchRecyclerViewAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if( !recyclerView.canScrollVertically(1)){
                    movieListViewModel.searchNextPage();
                }
            }
        });


    }
    private void startDescription(int position) {
        Intent intent = new Intent(getContext(), Description_movie.class);
        intent.putExtra("movie",searchRecyclerViewAdapter.getSelectedMovieId(position));
        startActivity(intent);
    }




}
