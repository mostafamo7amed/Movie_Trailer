package com.mostafa.moviejsonversion1.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mostafa.moviejsonversion1.Models.MovieModel;
import com.mostafa.moviejsonversion1.R;

import java.util.List;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<MovieModel> mMovies;
    OnMoviesListener onMoviesListener;

    public void setOnMoviesListener(OnMoviesListener listener){
        onMoviesListener = listener;
    }


    public SearchRecyclerViewAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);
            return new SearchViewHolder(view,onMoviesListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
             ((SearchViewHolder)holder).title.setText(mMovies.get(position).getTitle());
            String path = mMovies.get(position).getPoster_path();
            if(path != null) {
            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500" + mMovies.get(position).getPoster_path())
                    .into(((SearchViewHolder) holder).imageView);
            }


    }

    @Override
    public int getItemCount() {
        if(mMovies != null)
           return mMovies.size();
        else
            return 0;
    }

    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
    }

    public MovieModel getSelectedMovieId(int position){
        if(mMovies != null){
            if(mMovies.size() > 0){
                return mMovies.get(position);
            }
        }
        return null;
    }
}
