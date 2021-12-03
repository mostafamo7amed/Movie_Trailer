package com.mostafa.moviejsonversion1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.mostafa.moviejsonversion1.Models.MovieModel;
import com.mostafa.moviejsonversion1.R;
import com.mostafa.moviejsonversion1.Utils.Constants;

import java.util.List;


public class BannerMovieAdapter extends PagerAdapter {
    Context context;
    List<MovieModel> bannerMovieList;

    public BannerMovieAdapter(Context context , List<MovieModel> bannerMovieList) {
        this.context = context;
        this.bannerMovieList = bannerMovieList;
    }

    @Override
    public int getCount() {
        return bannerMovieList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
        ImageView imageView = view.findViewById(R.id.image_banner);
        String url =bannerMovieList.get(position).getBackdrop_path();
        if(url != null) {
            Glide.with(context)
                    .load(Constants.IMAGE_URL + bannerMovieList.get(position).getBackdrop_path())
                    .into(imageView);
            container.addView(view);
        }
        return view;
    }
}
