package com.mostafa.moviejsonversion1.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mostafa.moviejsonversion1.Models.MovieModel;
import com.mostafa.moviejsonversion1.Models.TrendingModel;
import com.mostafa.moviejsonversion1.R;
import com.mostafa.moviejsonversion1.Utils.Constants;

import java.util.List;

public class TrendingRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<TrendingModel> mTrend;
    OnMoviesListener onMoviesListener;

    public void setOnMoviesListener(OnMoviesListener listener){
        onMoviesListener = listener;
    }


    public TrendingRecyclerViewAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trend_item,parent,false);
            return new TrendingViewHolder(view,onMoviesListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
             String media = mTrend.get(position).getMedia_type();
             ((TrendingViewHolder)holder).title.setText(mTrend.get(position).getName());
             String path = mTrend.get(position).getPoster_path();
             if(path != null) {
                 Glide.with(holder.itemView.getContext())
                             .load(Constants.IMAGE_URL + mTrend.get(position).getPoster_path())
                             .into(((TrendingViewHolder) holder).imageView);
             } else {
                 Glide.with(holder.itemView.getContext())
                             .load(Constants.IMAGE_URL + mTrend.get(position).getBackdrop_path())
                             .into(((TrendingViewHolder) holder).imageView);
                 }







    }

    @Override
    public int getItemCount() {
        if(mTrend != null)
           return mTrend.size();
        else
            return 0;
    }

    public void setmTrend(List<TrendingModel> mTrend) {
        this.mTrend = mTrend;
    }

    public TrendingModel getSelectedTrendingId(int position){
        if(mTrend != null){
            if(mTrend.size() > 0){
                return mTrend.get(position);
            }
        }
        return null;
    }
}
