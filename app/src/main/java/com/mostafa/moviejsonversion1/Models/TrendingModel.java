package com.mostafa.moviejsonversion1.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrendingModel implements Parcelable{
    private String release_date;
    private String title;
    private String poster_path;
    private String backdrop_path;
    private String profile_path;
    private String known_for_department;
    private float popularity;
    @SerializedName("overview")
    @Expose
    private  String overview;
    private float vote_average;
    private int id;
    private  String name;
    private  String first_air_date;
    private  String media_type;

    public TrendingModel (String release_date, String title, String poster_path, String backdrop_path, String overview, float vote_average, int id,String name,String first_air_date,String media_type,String profile_path, String known_for_department, float popularity) {
        this.release_date = release_date;
        this.title = title;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.vote_average = vote_average;
        this.id = id;
        this.first_air_date=first_air_date;
        this.name = name;
        this.media_type=media_type;
        this.popularity = popularity;
        this.known_for_department = known_for_department;
        this.profile_path = profile_path;
    }

    protected TrendingModel (Parcel in) {
        release_date = in.readString();
        title = in.readString();
        poster_path = in.readString();
        vote_average = in.readFloat();
        id = in.readInt();
        overview = in.readString();
        backdrop_path =in.readString();
        name = in.readString();
        first_air_date = in.readString();
        media_type = in.readString();
        popularity = in.readFloat();
        profile_path = in.readString();
        known_for_department = in.readString();
    }

    public static final Parcelable.Creator<TrendingModel> CREATOR = new Parcelable.Creator<TrendingModel>() {
        @Override
        public TrendingModel createFromParcel(Parcel in) {
            return new TrendingModel(in);
        }

        @Override
        public TrendingModel[] newArray(int size) {
            return new TrendingModel[size];
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

    public int getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getName() {
        return name;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public String getMedia_type() {
        return media_type;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public float getPopularity() {
        return popularity;
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
        dest.writeString(name);
        dest.writeString(first_air_date);
        dest.writeString(media_type);
        dest.writeString(known_for_department);
        dest.writeString(profile_path);
        dest.writeFloat(popularity);

    }

}
