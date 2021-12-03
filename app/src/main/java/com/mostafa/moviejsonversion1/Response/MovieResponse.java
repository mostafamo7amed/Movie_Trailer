package com.mostafa.moviejsonversion1.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mostafa.moviejsonversion1.Models.MovieModel;

//this class for single movie response
public class MovieResponse {
    @SerializedName("results")
    @Expose
    private MovieModel movie;

    public MovieModel getMovie(){
        return movie;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movie=" + movie +
                '}';
    }
}
