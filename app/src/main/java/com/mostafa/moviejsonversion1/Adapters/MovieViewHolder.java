package com.mostafa.moviejsonversion1.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mostafa.moviejsonversion1.R;

public class MovieViewHolder extends RecyclerView.ViewHolder {
    TextView title,rate,date,movieId;
    ImageView imageView;
    OnMoviesListener onMoviesListener;
    public MovieViewHolder(@NonNull View itemView, final OnMoviesListener Listener) {
        super(itemView);
        title = itemView.findViewById(R.id.name_text);
        rate = itemView.findViewById(R.id.rat_text);
        date = itemView.findViewById(R.id.id_text);
        imageView =itemView.findViewById(R.id.imageView);
        movieId = itemView.findViewById(R.id.movie_Id);

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
