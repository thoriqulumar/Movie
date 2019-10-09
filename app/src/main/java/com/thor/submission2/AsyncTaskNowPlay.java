package com.thor.submission2;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class AsyncTaskNowPlay extends AsyncTaskLoader<ArrayList<Movie>> {
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;

    private ArrayList<Movie> mMovieList ;
    private boolean mResult = false;

    public AsyncTaskNowPlay(final Context context) {
        super(context);
        onContentChanged();

    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (takeContentChanged()){
            forceLoad();
        }else if (mResult){
            deliverResult(mMovieList);
        }
    }

    @Override
    public void deliverResult(@Nullable ArrayList<Movie> data) {
        mMovieList = data;
        mResult =true;
        super.deliverResult(data);

    }

    @Nullable
    @Override
    public ArrayList<Movie> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<Movie> mMovie = new ArrayList<>();
        String NOW_PLAY_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key="+API_KEY+"&language=en-US";

        client.get(NOW_PLAY_URL, new AsyncHttpResponseHandler() {
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
