package com.mostafa.moviejsonversion1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mostafa.moviejsonversion1.Fragments.Details_fragment;
import com.mostafa.moviejsonversion1.Fragments.Overview_fragment;
import com.mostafa.moviejsonversion1.Fragments.Reviews_fragment;
import com.mostafa.moviejsonversion1.Models.MovieModel;
import com.mostafa.moviejsonversion1.R;
import com.mostafa.moviejsonversion1.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Description_movie extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ImageView imageView;
    TextView title,rate,date,time,play;
    String details_url,video_url;
    MovieModel movieModel;
    int budgete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_movie);
        imageView =findViewById(R.id.imageView2);
        title =findViewById(R.id.tx_des_title);
        rate =findViewById(R.id.rate_full_tx);
        date =findViewById(R.id.date_full_tx);
        time = findViewById(R.id.time_full_tx);
        play= findViewById(R.id.tx_des_play);
        bottomNavigationView = findViewById(R.id.bottom_nav_des);

        if(getIntent().hasExtra("movie")){
            movieModel= getIntent().getParcelableExtra("movie");
            Glide.with(this)
                    .load(Constants.IMAGE_URL +movieModel.getPoster_path())
                    .into(imageView);
            title.setText(movieModel.getTitle());
            rate.setText( movieModel.getVote_average()+"");
            String dat =movieModel.getRelease_date();
            String year="";
            if(dat != null){
                if (dat.length() == 10) {
                    year = dat.substring(0,4);
                }
            }
            date.setText(year);
        }
        Log.v("tadd",movieModel.getid()+" ");
        details_url ="https://api.themoviedb.org/3/movie/"+movieModel.getid()+"?api_key=9c4875bc92cc8e91386efc5e0fc806d2";
        video_url ="https://api.themoviedb.org/3/movie/"+movieModel.getid()+"/videos?api_key=9c4875bc92cc8e91386efc5e0fc806d2";
        GetTime getDetails = new GetTime(details_url);
        getDetails.execute();


        bottomNavigationView.setOnItemSelectedListener(OnSelect);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetVideo getVideo = new GetVideo(video_url);
                getVideo.execute();
            }
        });


        Fragment fragment = new Details_fragment();
        Bundle bundle = new Bundle();
        bundle.putInt("movie_id",movieModel.getid());
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_des, fragment).commit();
        Constants.current_fragment = 1;


    }
    private final BottomNavigationView.OnItemSelectedListener OnSelect = new BottomNavigationView.OnItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selected = null;
            int select =0;

            switch (item.getItemId()) {
                case R.id.details: {
                    selected = new Details_fragment();
                    select = 1;
                    if(getIntent().hasExtra("movie")) {
                        MovieModel movieModel = getIntent().getParcelableExtra("movie");
                        Bundle bundle = new Bundle();
                        bundle.putInt("movie_id",movieModel.getid());
                        selected.setArguments(bundle);
                    }


                }
                break;
                case R.id.overview: {
                    selected = new Overview_fragment();
                    select = 2;
                    Bundle bundle = new Bundle();
                    bundle.putString("overView",movieModel.getOverview());
                    selected.setArguments(bundle);
                }
                break;
                case R.id.reviews: {
                    selected = new Reviews_fragment();
                    select = 3;
                    if(getIntent().hasExtra("movie")) {
                        MovieModel movieModel = getIntent().getParcelableExtra("movie");
                        Bundle bundle = new Bundle();
                        bundle.putInt("movie_id",movieModel.getid());
                        selected.setArguments(bundle);
                    }
                }
                break;
            }
            if (selected != null) {
                if (Constants.current_fragment != select) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_des, selected).commit();
                    Constants.current_fragment = select;
                } else {
                    return true;
                }
            }
            return true;
        }

    };

    public void leaveDesMovie(View view) {
        this.finish();
    }

    public class GetTime extends AsyncTask<String,String,String> {
        String ur;
        java.util.List<MovieModel> List;


        public GetTime(String ur) {
            this.ur = ur;
        }

        @Override
        protected String doInBackground(String... strings) {
            String current ="";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(ur);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int Data =isr.read();
                    while(Data != -1)
                    {
                        current += (char) Data;
                        Data = isr.read();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(urlConnection != null)
                        urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                int timee = (int)jsonObject.getInt("runtime");
                if(timee>0) {
                    int h = timee / 60;
                    int m = timee - (h * 60);
                    time.setText(String.format("%dh%dm", h, m));
                }else{
                    time.setText(String.format("%dh%dm", 0, 0));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public class GetVideo extends AsyncTask<String,String,String> {
        String ur;
        ProgressDialog dialog;


        public GetVideo(String ur) {
            this.ur = ur;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Description_movie.this);
            dialog.show();
            dialog.setContentView(R.layout.progress_dialog);
            dialog.setProgressStyle(R.style.Widget_MaterialComponents_CircularProgressIndicator);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        @Override
        protected String doInBackground(String... strings) {
            String current ="";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(ur);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int Data =isr.read();
                    while(Data != -1)
                    {
                        current += (char) Data;
                        Data = isr.read();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(urlConnection != null)
                        urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            String key="";
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                JSONObject jsonObject1= jsonArray.getJSONObject(0);
                key = jsonObject1.getString("key");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(Description_movie.this, VideoPlayer.class);
            intent.putExtra("key",key);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}