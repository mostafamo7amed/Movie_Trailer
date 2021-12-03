package com.mostafa.moviejsonversion1.Repositorys;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mostafa.moviejsonversion1.Models.MovieModel;
import com.mostafa.moviejsonversion1.Models.ReviewModel;
import com.mostafa.moviejsonversion1.Models.TrendingModel;
import com.mostafa.moviejsonversion1.Request.MovieApiClient;

import java.util.List;

public class MovieRepository {

    private MovieApiClient movieApiClient;

    private static MovieRepository instance;
    private String mQuery;
    private String tQuery;
    private int mPageNumber;


    public static MovieRepository getInstance() {
        if(instance ==null)
            instance =new MovieRepository();

        return instance;
    }
    public MovieRepository() {
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieApiClient.getMovies();
    }
    public LiveData<List<MovieModel>> getMoviesPop() {
        return movieApiClient.getMoviesPop();
    }
    public LiveData<List<MovieModel>> getMoviesUpcoming() {
        return movieApiClient.getUpcoming();
    }
    public LiveData<List<MovieModel>> getMoviesTopRated() {
        return movieApiClient.getTopRated();
    }
    public LiveData<List<MovieModel>> getMoviesNowPlaying() {
        return movieApiClient.getNowPlaying();
    }
    public LiveData<List<TrendingModel>> getTrending() {
        return movieApiClient.getTrending();
    }


    public void searchMovieApi(String query , int pageNumber){
        mQuery = query;
        mPageNumber = pageNumber;
        movieApiClient.searchMovieApi(query,pageNumber);
    }
    public void searchTrending( int pageNumber ,String type ,String date){
        mQuery = type;
        tQuery =date;
        mPageNumber = pageNumber;
        movieApiClient.searchTrending(pageNumber, type ,date);
    }
    public void searchMoviePopular(int pageNumber){
        movieApiClient.searchMoviePopular(pageNumber);
    }
    public void searchMovieUpcoming(int pageNumber){
        movieApiClient.searchMovieUpcoming(pageNumber);
    }
    public void searchMovieTopRated(int pageNumber){
        movieApiClient.searchMovieTopRated(pageNumber);
    }
    public void searchMovieNowPlaying(int pageNumber){
        movieApiClient.searchMovieNowPlaying(pageNumber);
    }

    public void searchNextPage(){
        movieApiClient.searchMovieApi(mQuery,mPageNumber+=1);
    }
    public void searchNextPopular(){
        movieApiClient.searchMoviePopular(mPageNumber+=1);
    }
    public void searchNextTopRated(){
        movieApiClient.searchMovieTopRated(mPageNumber+=1);
    }
    public void searchNextUpcoming(){
        movieApiClient.searchMovieUpcoming(mPageNumber+=1);
    }
    public void searchNextNowPlaying(){
        movieApiClient.searchMovieNowPlaying(mPageNumber+=1);
    }
    public void searchNextTrending(){
        movieApiClient.searchTrending(mPageNumber+=1 , mQuery ,tQuery);
    }

}
