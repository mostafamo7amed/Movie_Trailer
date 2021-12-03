package com.mostafa.moviejsonversion1.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mostafa.moviejsonversion1.Models.MovieModel;
import com.mostafa.moviejsonversion1.Models.TrendingModel;

import java.util.List;

public class TrendingSearchResponse {
    @SerializedName("total_results")
    @Expose
    private int total_count;

    @SerializedName("results")
    @Expose
    private List<TrendingModel> trend;

    public int getTotal_count(){
        return total_count;
    }

    public List<TrendingModel> getTrending(){
        return trend;
    }

    @Override
    public String toString() {
        return "TrendingSearchResponse{" +
                "total_count=" + total_count +
                ", trend=" + trend +
                '}';
    }
}
