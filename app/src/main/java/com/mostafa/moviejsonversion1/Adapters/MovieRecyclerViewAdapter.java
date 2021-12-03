package com.mostafa.moviejsonversion1.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.mostafa.moviejsonversion1.Models.MovieModel;
import com.mostafa.moviejsonversion1.R;
import com.mostafa.moviejsonversion1.Utils.Constants;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<MovieModel> mMovies;
    OnMoviesListener onMoviesListener;

    public void setOnMoviesListener(OnMoviesListener listener){
        onMoviesListener = listener;
    }

    public MovieRecyclerViewAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);
            return new MovieViewHolder(view,onMoviesListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
             Shimmer shimmer= new Shimmer.ColorHighlightBuilder()
                     .setBaseColor(Color.parseColor("#0b194d"))
                     .setBaseAlpha(1)
                     .setHighlightColor(Color.parseColor("#0b194d"))
                     .setHighlightAlpha(1)
                     .setDropoff(50)
                     .build();
             ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
             shimmerDrawable.setShimmer(shimmer);

             ((MovieViewHolder)holder).title.setText(mMovies.get(position).getTitle());
             ((MovieViewHolder)holder).rate.setText(String.valueOf(mMovies.get(position).getVote_average()));
             ((MovieViewHolder)holder).date.setText(mMovies.get(position).getRelease_date());
             //((MovieViewHolder)holder).movieId.setText(mMovies.get(position).getid());
              String path = mMovies.get(position).getPoster_path();
             if(path != null) {
                 Glide.with(holder.itemView.getContext())
                         .load(Constants.IMAGE_URL + mMovies.get(position).getPoster_path())
                         .placeholder(shimmerDrawable)
                         .into(((MovieViewHolder) holder).imageView);
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
    public List<MovieModel> getmMovies() {
        return mMovies;
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
