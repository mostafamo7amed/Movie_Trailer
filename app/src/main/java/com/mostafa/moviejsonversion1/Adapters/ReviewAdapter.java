package com.mostafa.moviejsonversion1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mostafa.moviejsonversion1.Models.ReviewModel;
import com.mostafa.moviejsonversion1.R;
import com.mostafa.moviejsonversion1.Utils.Constants;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    List<ReviewModel> reviewModels;
    Context context;

    OnReviewListener onReviewListener;

    public void setOnReviewListener(OnReviewListener listener){
        onReviewListener = listener;
    }

    public ReviewAdapter(Context context, List<ReviewModel> reviewModels) {
        this.context=context;
        this.reviewModels = reviewModels;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item,parent,false);
        return new ReviewViewHolder(view,onReviewListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        ((ReviewViewHolder)holder).name.setText(reviewModels.get(position).getUsername());
        String rate=reviewModels.get(position).getUpdated_at();
        if(rate.equals("null")) {
            String rte="0.0";
            ((ReviewViewHolder) holder).time.setText(rte);

        }
        else {
            ((ReviewViewHolder) holder).time.setText(rate+".0");
        }
        ((ReviewViewHolder)holder).content.setText(reviewModels.get(position).getContent());
        String path = reviewModels.get(position).getAvatar_path();

        if(path.equals("null")) {
            Glide.with(holder.itemView.getContext())
                    .load("https://www.irishrsa.ie/wp-content/uploads/2017/03/default-avatar.png")
                    .into(((ReviewViewHolder) holder).imageView);
        }
        else {
            if(path.length() < 35) {
                Glide.with(holder.itemView.getContext())
                        .load(Constants.IMAGE_URL + path)
                        .into(((ReviewViewHolder) holder).imageView);
            }else {
                String url = path.substring(1);
                Glide.with(holder.itemView.getContext())
                        .load(url)
                        .into(((ReviewViewHolder) holder).imageView);
            }
        }


    }

    @Override
    public int getItemCount() {
        if(reviewModels != null)
            return reviewModels.size();
        else
            return 0;
    }

    public List<ReviewModel> getReviewModels() {
        return reviewModels;
    }

    public void setReviewModels(List<ReviewModel> reviewModels) {
        this.reviewModels = reviewModels;
    }
    public ReviewModel getSelectedMovieId(int position){
        if(reviewModels != null){
            if(reviewModels.size() > 0){
                return reviewModels.get(position);
            }
        }
        return null;
    }
}
