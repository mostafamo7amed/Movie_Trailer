package com.mostafa.moviejsonversion1.Request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mostafa.moviejsonversion1.AppExecutors;
import com.mostafa.moviejsonversion1.Models.MovieModel;
import com.mostafa.moviejsonversion1.Models.ReviewModel;
import com.mostafa.moviejsonversion1.Models.TrendingModel;
import com.mostafa.moviejsonversion1.Response.MovieSearchResponse;
import com.mostafa.moviejsonversion1.Response.TrendingSearchResponse;
import com.mostafa.moviejsonversion1.Utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    private static MovieApiClient instance;
    //search
    private final MutableLiveData<List<MovieModel>> mMovies;
    private RetrieveMovieRunnable retrieveMovieRunnable;
    //popular
    private final MutableLiveData<List<MovieModel>> mMoviesPop;
    private RetrieveMovieRunnablePop retrieveMovieRunnablePop;
    //upcoming
    private final MutableLiveData<List<MovieModel>> mUpcoming;
    private RetrieveMovieRunnableUpcoming rUpcoming;
    //TopRated
    private final MutableLiveData<List<MovieModel>> mTopRated;
    private RetrieveMovieRunnableTopRated rTopRated;
    //now Playing
    private final MutableLiveData<List<MovieModel>> mNowPlaying;
    private RetrieveMovieRunnableNowPlaying rNowPlaying;

    private final MutableLiveData<List<TrendingModel>> trending;
    private RetrieveTrendingRunnable rtrending;


    public static MovieApiClient getInstance(){
        if(instance == null)
            instance = new MovieApiClient();
        return instance;
    }

    private MovieApiClient(){
        mMovies = new MutableLiveData<>();
        mMoviesPop = new MutableLiveData<>();
        mUpcoming = new MutableLiveData<>();
        mTopRated = new MutableLiveData<>();
        mNowPlaying = new MutableLiveData<>();
        trending = new MutableLiveData<>();
    }



    public LiveData<List<MovieModel>> getMovies() {
        return mMovies;
    }
    public LiveData<List<MovieModel>> getMoviesPop() {
        return mMoviesPop;
    }
    public LiveData<List<MovieModel>> getUpcoming() {
        return mUpcoming;
    }
    public LiveData<List<MovieModel>> getTopRated() {
        return mTopRated;
    }
    public LiveData<List<MovieModel>> getNowPlaying() {
        return mNowPlaying;
    }
    public LiveData<List<TrendingModel>> getTrending() {
        return trending;
    }



    public void searchMovieApi(String query, int pageNumber){
        if(retrieveMovieRunnable != null){
            retrieveMovieRunnable = null;
        }
        retrieveMovieRunnable = new RetrieveMovieRunnable(query ,pageNumber);

        final Future myHandler = AppExecutors.getInstance().NetworkIO().submit(retrieveMovieRunnable);

        AppExecutors.getInstance().NetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },3000, TimeUnit.MILLISECONDS);
    }
    public void searchTrending(int pageNumber, String type ,String date){
        if(rtrending != null){
            rtrending = null;
        }
        rtrending = new RetrieveTrendingRunnable(pageNumber,type ,date);

        final Future myHandler = AppExecutors.getInstance().NetworkIO().submit(rtrending);

        AppExecutors.getInstance().NetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },1000, TimeUnit.MILLISECONDS);
    }
    public void searchMoviePopular(int pageNumber){
        if(retrieveMovieRunnablePop != null){
            retrieveMovieRunnablePop = null;
        }

        retrieveMovieRunnablePop = new RetrieveMovieRunnablePop(pageNumber);

        final Future myHandlerPop = AppExecutors.getInstance().NetworkIO().submit(retrieveMovieRunnablePop);

        AppExecutors.getInstance().NetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandlerPop.cancel(true);
            }
        },1000, TimeUnit.MILLISECONDS);
    }
    public void searchMovieUpcoming(int pageNumber){
        if(rUpcoming != null){
            rUpcoming = null;
        }

        rUpcoming = new RetrieveMovieRunnableUpcoming(pageNumber);

        final Future myHandlerUp= AppExecutors.getInstance().NetworkIO().submit(rUpcoming);

        AppExecutors.getInstance().NetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandlerUp.cancel(true);
            }
        },1000, TimeUnit.MILLISECONDS);
    }
    public void searchMovieTopRated(int pageNumber){
        if(rTopRated != null){
            rTopRated = null;
        }

        rTopRated = new RetrieveMovieRunnableTopRated(pageNumber);

        final Future myHandlerTop= AppExecutors.getInstance().NetworkIO().submit(rTopRated);

        AppExecutors.getInstance().NetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandlerTop.cancel(true);
            }
        },1000, TimeUnit.MILLISECONDS);
    }
    public void searchMovieNowPlaying(int pageNumber){
        if(rNowPlaying != null){
            rNowPlaying = null;
        }

        rNowPlaying = new RetrieveMovieRunnableNowPlaying(pageNumber);

        final Future myHandlerNow= AppExecutors.getInstance().NetworkIO().submit(rNowPlaying);

        AppExecutors.getInstance().NetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandlerNow.cancel(true);
            }
        },1000, TimeUnit.MILLISECONDS);
    }


    private class RetrieveMovieRunnable implements Runnable{

        private String query ;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMovieRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getMovies(query,pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if(response.code() == 200){
                    List<MovieModel> list =new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if (pageNumber == 1){
                        mMovies.postValue(list);
                    }else {
                        List<MovieModel> currentMovie =mMovies.getValue();
                        currentMovie.addAll(list);
                        mMovies.postValue(currentMovie);
                    }
                }else {
                    mMovies.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }

        }
        private Call<MovieSearchResponse> getMovies(String query ,int pageNumber) {
            return ServiceY.getMovieApi().searchMovie(
                    Constants.API_KEY,
                    query,
                    pageNumber
            );
        }
        private void cancelRequest (){
            cancelRequest = true;
        }

    }
    private class RetrieveTrendingRunnable implements Runnable{

        private String type ;
        private String date ;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveTrendingRunnable(int pageNumber , String type ,String date) {
            this.date=date;
            this.type=type;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getTrending(pageNumber ,type ,date).execute();
                if (cancelRequest) {
                    return;
                }
                if(response.code() == 200){
                    List<TrendingModel> list =new ArrayList<>(((TrendingSearchResponse)response.body()).getTrending());
                    if (pageNumber == 1){
                        trending.postValue(list);
                    }else {
                        List<TrendingModel> currentMovie =trending.getValue();
                        currentMovie.addAll(list);
                        trending.postValue(currentMovie);
                    }
                }else {
                    trending.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                trending.postValue(null);
            }

        }
        private Call<TrendingSearchResponse> getTrending(int pageNumber,String type ,String date) {
            return ServiceY.getMovieApi().getTrending(

                    type,
                    date,
                    Constants.API_KEY,
                    pageNumber
            );
        }
        private void cancelRequest (){
            cancelRequest = true;
        }

    }
    private class RetrieveMovieRunnablePop implements Runnable{
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMovieRunnablePop( int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response responsePop = getMoviesPop(pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if(responsePop.code() == 200){
                    List<MovieModel> list =new ArrayList<>(((MovieSearchResponse)responsePop.body()).getMovies());
                    if (pageNumber == 1){
                        mMoviesPop.postValue(list);
                    }else {
                        List<MovieModel> currentMovie =mMoviesPop.getValue();
                        currentMovie.addAll(list);
                        mMoviesPop.postValue(currentMovie);
                    }
                }else {
                    mMoviesPop.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mMoviesPop.postValue(null);
            }

        }
        private Call<MovieSearchResponse> getMoviesPop(int pageNumber) {
            return ServiceY.getMovieApi().getPopular(
                    Constants.API_KEY,
                    pageNumber
            );
        }
        private void cancelRequest (){
            cancelRequest = true;
        }

    }
    private class RetrieveMovieRunnableUpcoming implements Runnable{
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMovieRunnableUpcoming( int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response responseUp = getUpcoming(pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if(responseUp.code() == 200){
                    List<MovieModel> list =new ArrayList<>(((MovieSearchResponse)responseUp.body()).getMovies());
                    if (pageNumber == 1){
                        mUpcoming.postValue(list);
                    }else {
                        List<MovieModel> currentMovie =mUpcoming.getValue();
                        currentMovie.addAll(list);
                        mUpcoming.postValue(currentMovie);
                    }
                }else {
                    mUpcoming.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mUpcoming.postValue(null);
            }

        }
        private Call<MovieSearchResponse> getUpcoming(int pageNumber) {
            return ServiceY.getMovieApi().getUpcoming(
                    Constants.API_KEY,
                    pageNumber
            );
        }
        private void cancelRequest (){
            cancelRequest = true;
        }

    }
    private class RetrieveMovieRunnableTopRated implements Runnable{
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMovieRunnableTopRated( int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response responseTop= getTopRated(pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if(responseTop.code() == 200){
                    List<MovieModel> list =new ArrayList<>(((MovieSearchResponse)responseTop.body()).getMovies());
                    if (pageNumber == 1){
                        mTopRated.postValue(list);
                    }else {
                        List<MovieModel> currentMovie =mTopRated.getValue();
                        currentMovie.addAll(list);
                        mTopRated.postValue(currentMovie);
                    }
                }else {
                    mTopRated.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mTopRated.postValue(null);
            }

        }
        private Call<MovieSearchResponse> getTopRated(int pageNumber) {
            return ServiceY.getMovieApi().getTopRated(
                    Constants.API_KEY,
                    pageNumber
            );
        }
        private void cancelRequest (){
            cancelRequest = true;
        }

    }
    private class RetrieveMovieRunnableNowPlaying implements Runnable{
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMovieRunnableNowPlaying( int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response responseNow= getNowPlaying(pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if(responseNow.code() == 200){
                    List<MovieModel> list =new ArrayList<>(((MovieSearchResponse)responseNow.body()).getMovies());
                    if (pageNumber == 1){
                        mNowPlaying.postValue(list);
                    }else {
                        List<MovieModel> currentMovie =mNowPlaying.getValue();
                        currentMovie.addAll(list);
                        mNowPlaying.postValue(currentMovie);
                    }
                }else {
                    mNowPlaying.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mNowPlaying.postValue(null);
            }

        }
        private Call<MovieSearchResponse> getNowPlaying(int pageNumber) {
            return ServiceY.getMovieApi().getNowPlaying(
                    Constants.API_KEY,
                    pageNumber
            );
        }
        private void cancelRequest (){
            cancelRequest = true;
        }

    }


}
