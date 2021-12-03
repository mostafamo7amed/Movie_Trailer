package com.mostafa.moviejsonversion1.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mostafa.moviejsonversion1.R;

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    TextView name,content,time;
    ImageView imageView;

    public ReviewViewHolder(@NonNull View itemView, OnReviewListener onReviewListener) {
        super(itemView);
        name = itemView.findViewById(R.id.tx_rev_name);
        content = itemView.findViewById(R.id.tx_rev_content);
        time = itemView.findViewById(R.id.tx_rev_time);
        imageView = itemView.findViewById(R.id.rev_image);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onReviewListener!= null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        onReviewListener.onReviewClick(position);
                    }

                }
            }
        });
    }
}
