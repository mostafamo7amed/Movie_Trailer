package com.mostafa.moviejsonversion1.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewModel {


    private String content;
    private String updated_at;
    private String username;
    private String avatar_path;

    public ReviewModel(String content, String updated_at, String username, String avatar_path) {
        this.content = content;
        this.updated_at = updated_at;
        this.username = username;
        this.avatar_path = avatar_path;
    }

    public ReviewModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar_path() {
        return avatar_path;
    }

    public void setAvatar_path(String avatar_path) {
        this.avatar_path = avatar_path;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
