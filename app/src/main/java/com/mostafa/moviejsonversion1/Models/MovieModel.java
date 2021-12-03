package com.mostafa.moviejsonversion1.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieModel implements Parcelable {
    private String release_date;
    private String title;
    private String poster_path;
    private String backdrop_path;
    @SerializedName("overview")
    @Expose
    private  String overview;
    private float vote_average;
    private int id;

    public MovieModel(String release_date, String title, String poster_path, String backdrop_path, String overview, float vote_average, int id) {
        this.release_date = release_date;
        this.title = title;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.vote_average = vote_average;
        this.id = id;
    }

    protected MovieModel(Parcel in) {
        release_date = in.readString();
        title = in.readString();
        poster_path = in.readString();
        vote_average = in.readFloat();
        id = in.readInt();
        overview = in.readString();
        backdrop_path =in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public String getRelease_date() {
        return release_date;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public float getVote_average() {
        return vote_average;
    }

    public int getid() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(release_date);
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeFloat(vote_average);
        dest.writeInt(id);
        dest.writeString(overview);
        dest.writeString(backdrop_path);

    }

}
