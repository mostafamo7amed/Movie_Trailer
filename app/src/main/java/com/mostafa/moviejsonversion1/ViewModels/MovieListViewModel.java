package com.mostafa.moviejsonversion1.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mostafa.moviejsonversion1.Models.MovieModel;
import com.mostafa.moviejsonversion1.Models.ReviewModel;
import com.mostafa.moviejsonversion1.Models.TrendingModel;
import com.mostafa.moviejsonversion1.Repositorys.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {
    private final MovieRepository movieRepository;

    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }


    public LiveData<List<MovieModel>> getMovies() {
        return movieRepository.getMovies();
    }
    public LiveData<List<MovieModel>> getMoviesPop() {
        return movieRepository.getMoviesPop();
    }
    public LiveData<List<MovieModel>> getMoviesUpcoming() {
        return movieRepository.getMoviesUpcoming();
    }
    public LiveData<List<MovieModel>> getMoviesTopRated() {
        return movieRepository.getMoviesTopRated();
    }
    public LiveData<List<MovieModel>> getMoviesNowPlaying() {
        return movieRepository.getMoviesNowPlaying();
    }
    public LiveData<List<TrendingModel>> getTrending() {
        return movieRepository.getTrending();
    }

    public void searchMovieApi(String query , int pageNumber){
        movieRepository.searchMovieApi(query,pageNumber);
    }
    public void searchTrending(int pageNumber , String type ,String date){
        movieRepository.searchTrending(pageNumber ,type ,date);
    }
    public void searchMoviePopular(int pageNumber){
        movieRepository.searchMoviePopular(pageNumber);
    }
    public void searchMovieUpcoming(int pageNumber){
        movieRepository.searchMovieUpcoming(pageNumber);
    }
    public void searchMovieTopRated(int pageNumber){
        movieRepository.searchMovieTopRated(pageNumber);
    }
    public void searchMovieNowPlaying(int pageNumber){
        movieRepository.searchMovieNowPlaying(pageNumber);
    }


    public void searchNextPage(){
        movieRepository.searchNextPage();
    }
    public void searchNextPopular(){
        movieRepository.searchNextPopular();
    }
    public void searchNextUpcoming(){
        movieRepository.searchNextUpcoming();
    }
    public void searchNextTopRated(){
        movieRepository.searchNextTopRated();
    }
    public void searchNextNowPlaying(){
        movieRepository.searchNextNowPlaying();
    }
    public void searchNextTrending(){
        movieRepository.searchNextTrending();
    }



}
