package com.mostafa.moviejsonversion1.Fragments;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mostafa.moviejsonversion1.Adapters.ReviewAdapter;
import com.mostafa.moviejsonversion1.Models.ReviewModel;
import com.mostafa.moviejsonversion1.R;
import com.mostafa.moviejsonversion1.Request.ServiceY;
import com.mostafa.moviejsonversion1.Utils.Constants;
import com.mostafa.moviejsonversion1.Utils.MovieApi;
import com.mostafa.moviejsonversion1.ViewModels.MovieListViewModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Reviews_fragment extends Fragment {

    ReviewAdapter reviewAdapter;
    RecyclerView recyclerView;
    List<ReviewModel> reviewModels;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.rev_recycler);
        reviewModels = new ArrayList<>();

        Bundle bundle = this.getArguments();

        String details_url ="https://api.themoviedb.org/3/movie/"+bundle.getInt("movie_id")+"/reviews?api_key=9c4875bc92cc8e91386efc5e0fc806d2";
        GetReview review = new GetReview(details_url,reviewModels);
        review.execute();
        Log.v("Id ", " "+bundle.getInt("movie_id"));


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reviews_fragment, container, false);
    }
    public class GetReview extends AsyncTask<String,String,String> {
        String ur;
        List<ReviewModel> Models;
        ProgressDialog dialog;

        public GetReview(String ur, List<ReviewModel> models) {
            this.ur = ur;
            Models = models;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
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

            try {

                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i=0 ;i<jsonArray.length();i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    ReviewModel reviewModel = new ReviewModel();
                    reviewModel.setContent(jsonObject1.getString("content"));

                    String details = jsonObject1.getString("author_details");
                    JSONObject object = new JSONObject(details);
                    reviewModel.setUsername(object.getString("username"));
                    String path = object.getString("avatar_path");
                    reviewModel.setAvatar_path(path);
                    reviewModel.setUpdated_at(object.getString("rating"));


                    Log.v("rating"," "+(object.getString("rating")));

                    Models.add(reviewModel);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            putDataRecycler();
        }
    }

    private void putDataRecycler() {
        reviewAdapter = new ReviewAdapter(getContext(),reviewModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(reviewAdapter);
    }

}