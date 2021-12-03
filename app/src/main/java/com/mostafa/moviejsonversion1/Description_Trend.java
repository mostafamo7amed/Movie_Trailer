package com.mostafa.moviejsonversion1;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mostafa.moviejsonversion1.Models.MovieModel;
import com.mostafa.moviejsonversion1.Models.TrendingModel;
import com.mostafa.moviejsonversion1.Utils.Constants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Description_Trend extends AppCompatActivity {
    ImageView imageView;
    TextView title,rate,date,time,play,overview;
    String details_url,video_url;
    TrendingModel trendModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descreption__trend);
        imageView =findViewById(R.id.imageView3);
        title =findViewById(R.id.tx_trnd_title);
        rate =findViewById(R.id.rate_trnd_tx);
        date =findViewById(R.id.date_trnd_tx);
        time = findViewById(R.id.time_trnd_tx);
        overview = findViewById(R.id.over_trnd);

        if(getIntent().hasExtra("movie")){
            trendModel= getIntent().getParcelableExtra("movie");
            Glide.with(this)
                    .load(Constants.IMAGE_URL +trendModel.getPoster_path())
                    .into(imageView);
            title.setText(trendModel.getName());
            time.setText(trendModel.getMedia_type());
            rate.setText( trendModel.getVote_average()+"");
            String dat =trendModel.getFirst_air_date();
            String year="";
            if(dat != null){
                if (dat.length() == 10) {
                    year = dat.substring(0,4);
                }
            }
            date.setText(year);
            overview.setText(trendModel.getOverview());
        }
    }

    public void leaveDesTrend(View view) {
        this.finish();
    }
}