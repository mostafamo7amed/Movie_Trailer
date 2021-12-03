package com.mostafa.moviejsonversion1.Utils;

import com.mostafa.moviejsonversion1.Models.MovieModel;
import com.mostafa.moviejsonversion1.Response.MovieSearchResponse;
import com.mostafa.moviejsonversion1.Response.TrendingSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie (
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page

    );

    @GET("/3/movie/{movie_id}?")
    Call<MovieModel> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopular(
            @Query("api_key") String api_key,
            @Query("page") int page
    );

    @GET("/3/movie/upcoming")
    Call<MovieSearchResponse> getUpcoming(
            @Query("api_key") String api_key,
            @Query("page") int page
    );

    @GET("/3/movie/top_rated")
    Call<MovieSearchResponse> getTopRated(
            @Query("api_key") String api_key,
            @Query("page") int page
    );

    @GET("/3/movie/now_playing")
    Call<MovieSearchResponse> getNowPlaying(
            @Query("api_key") String api_key,
            @Query("page") int page
    );

    @GET("/3/trending/{media_type}/{time_window}")
    Call<TrendingSearchResponse> getTrending(
            @Path("media_type") String media_type,
            @Path("time_window") String time_window,
            @Query("api_key") String api_key,
            @Query("page") int page

    );
}
