package com.mostafa.moviejsonversion1.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mostafa.moviejsonversion1.Models.MovieModel;
import com.mostafa.moviejsonversion1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Details_fragment extends Fragment {
    TextView statusy,popularityy,countryy,languagey,budgety,genresy;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        statusy = (TextView) getActivity().findViewById(R.id.tx_det_status);
        popularityy = (TextView) getActivity().findViewById(R.id.tx_det_popularity);
        countryy = (TextView) getActivity().findViewById(R.id.tx_det_country);
        languagey = (TextView) getActivity().findViewById(R.id.tx_det_language);
        budgety = (TextView) getActivity().findViewById(R.id.tx_det_budget);
        genresy = (TextView) getActivity().findViewById(R.id.tx_det_genres);
        Bundle bundle = this.getArguments();
        String details_url ="https://api.themoviedb.org/3/movie/"+bundle.getInt("movie_id")+"?api_key=9c4875bc92cc8e91386efc5e0fc806d2";
        GetDetails getDetails= new GetDetails(details_url);
        getDetails.execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details_fragment, container, false);
    }
    public class GetDetails extends AsyncTask<String,String,String> {
        String ur;
        ProgressDialog dialog;


        public GetDetails(String ur) {
            this.ur = ur;
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


            String genres="",status="",language="",country="";
            int budget,popularity;

            try {

                JSONObject jsonObject = new JSONObject(s);
                status = jsonObject.getString("status");
                budget =(int)jsonObject.getInt("budget");
                popularity = (int)jsonObject.getInt("popularity");

                JSONArray jsonArray = jsonObject.getJSONArray("genres");
                for (int i=0 ;i<jsonArray.length();i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    if(i==jsonArray.length()-1){
                        genres += jsonObject1.getString("name");
                    } else {
                        genres += jsonObject1.getString("name") + " , ";
                    }

                }
                JSONArray jsonArrayLan = jsonObject.getJSONArray("spoken_languages");
                for (int i=0 ;i<jsonArrayLan.length();i++) {
                    JSONObject jsonObject1 = jsonArrayLan.getJSONObject(i);
                    if(i==jsonArrayLan.length()-1){
                        language += jsonObject1.getString("english_name");
                    } else {
                        language += jsonObject1.getString("english_name") + " , ";
                    }
                }
                JSONArray jsonArrayCun = jsonObject.getJSONArray("production_countries");
                for (int i=0 ;i<jsonArrayCun.length();i++) {
                    JSONObject jsonObject1 = jsonArrayCun.getJSONObject(i);
                    if(i==jsonArrayCun.length()-1){
                        country += jsonObject1.getString("name");
                    } else {
                        country += jsonObject1.getString("name") + " , ";
                    }
                }
                Log.v("tagg",status+"\n"+country+"\n"+popularity+"\n"+budget+"\n"+genres+"\n"+language);
                statusy.setText(status);
                countryy.setText(country);
                popularityy.setText(popularity+"");
                budgety.setText(budget+" $");
                genresy.setText(genres);
                languagey.setText(language);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();

    }
}