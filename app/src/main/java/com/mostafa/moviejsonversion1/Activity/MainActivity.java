
package com.mostafa.moviejsonversion1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.mostafa.moviejsonversion1.Fragments.Home_fragment;
import com.mostafa.moviejsonversion1.Fragments.Search_fragment;
import com.mostafa.moviejsonversion1.Fragments.Trending_fragment;
import com.mostafa.moviejsonversion1.Models.MovieModel;
import com.mostafa.moviejsonversion1.NetworkStatus.InternetCheckService;
import com.mostafa.moviejsonversion1.NetworkStatus.NetworkActive;
import com.mostafa.moviejsonversion1.Fragments.Profile_fragment;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        String status = NetworkActive.getNetworkStatus(this);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        broadcastReceiver = new InternetCheckService();
        bottomNavigationView.setOnItemSelectedListener(OnSelect);


        CheckConnect();
        //Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new Home_fragment()).commit();
        Constants.current=1;

    }

    private void CheckConnect() {
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private final BottomNavigationView.OnItemSelectedListener OnSelect = new BottomNavigationView.OnItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selected = null;
            int select=0;
            
            switch (item.getItemId()) {
                case R.id.home_btu: {
                    selected = new Home_fragment();
                    select =1;
                }
                break;
                case R.id.search_btu: {
                    selected = new Search_fragment();
                    select = 4;
                }
                break;
                case R.id.profile_btu: {
                    selected = new Profile_fragment();
                    select = 2;
                }
                break;
                case R.id.trend_btu: {
                    selected = new Trending_fragment();
                    select = 3;
                }
                break;
            }
            if (selected != null) {
                if(select != Constants.current) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selected).commit();
                    Constants.current = select;
                }
                else {
                    return true;
                }
            }
            return true;
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);

    }

    public class GetData extends AsyncTask<String,String,String> {
        String ur;
        RecyclerView recycler;
        List<MovieModel> List;


        public GetData(String ur, RecyclerView recycler,List<MovieModel> modelList) {
            this.ur = ur;
            this.recycler = recycler;
            this.List = modelList;
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
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i=0 ;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    //MovieModel model=new MovieModel();
                   // model.setId(jsonObject1.getString("release_date"));
                  //  model.setTitle(jsonObject1.getString("title"));
                    //model.setPoster_pat(jsonObject1.getString("poster_path"));
                  //  model.setVote_average(jsonObject1.getString("vote_average"));

                   // List.add(model);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(this, RegisterLogin.class);
            startActivity(intent);
            finish();
        }
    }



}