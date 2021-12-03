package com.mostafa.moviejsonversion1.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mostafa.moviejsonversion1.R;

public class TrendingViewHolder extends RecyclerView.ViewHolder {
    TextView title,rate;
    ImageView imageView;
    OnMoviesListener onMoviesListener;
    public TrendingViewHolder(@NonNull View itemView, final OnMoviesListener Listener) {
        super(itemView);
        title = itemView.findViewById(R.id.tx_name_trend);
        imageView =itemView.findViewById(R.id.imageTrend);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        Listener.onMovieClick(position);
                    }

                }
            }
        });
    }

}
