package com.mostafa.moviejsonversion1.Adapters;

import androidx.recyclerview.widget.RecyclerView;

public interface OnMoviesListener {

    void onMovieClick(int position);
    void onCategoryClick(String category);
}

