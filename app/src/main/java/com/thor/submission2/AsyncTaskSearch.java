package com.thor.submission2;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class AsyncTaskSearch extends AsyncTaskLoader<ArrayList<Movie>> {
    private boolean mResult = false;
    private ArrayList<Movie> mmovie;

    private String title;
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;

    public AsyncTaskSearch(final Context context,String title) {
        super(context);
        onContentChanged();
        this.title = title;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (takeContentChanged()){
            forceLoad();
        }else if (mResult){
            deliverResult(mmovie);
        }
    }

    @Override
    public void deliverResult(ArrayList<Movie> data) {
        mmovie =data;
        mResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mResult){
            mmovie = null;
            mResult =false;
        }
    }

    @Nullable
    @Override
    public ArrayList<Movie> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<Movie> mMovie = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&language=en-US&query="+title;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject movie = jsonArray.getJSONObject(i);
                        String title = movie.getString("title");
                        String overview = movie.getString("overview");
                        String posterPath = movie.getString("poster_path");

                        Movie movie1 = new Movie(title,overview,posterPath);

                        mMovie.add(movie1);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return mMovie;
    }
}
