package com.example.bening_2.movieuiux.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.bening_2.movieuiux.BuildConfig;
import com.example.bening_2.movieuiux.MovieItems;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Bening_2 on 3/5/2018.
 */

public class MovieUpcomingLoader extends AsyncTaskLoader<ArrayList<MovieItems>> {
    private ArrayList<MovieItems> mData;
    private Boolean mHasResult = false;

    public static String json;

    public MovieUpcomingLoader(Context context) {
        super(context);

        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<MovieItems> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    @Override
    public ArrayList<MovieItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItems> movieItemses = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key="+ BuildConfig.MYAPIKEY +"&language=en-US";

        Log.d("nilai url", ""+url);

        client.get(url+"", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                json = new String(responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("==========","ERROR===========");
            }
        });

        return movieItemses;
    }

    public static ArrayList<MovieItems> getListFilm() throws JSONException {
        MovieItems movieItems = null;
        ArrayList<MovieItems> movieList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray list = jsonObject.getJSONArray("results");

        for(int i = 0; i < list.length(); i++){
            String title1, lead1, date1;

            JSONObject movie = list.getJSONObject(i);
            String title = movie.getString("title");
            String lead = movie.getString("overview");
            String date = movie.getString("release_date");
            String path_photo = movie.getString("poster_path");
            String photo = "http://image.tmdb.org/t/p/w780"+path_photo;
            Log.d("JSON ARRAY INDEKS KE "+ i, ""+ title + "/" + lead + "/" + date + "/" + photo);


            if(title.isEmpty()){
                title1 = "-";
            } else {
                title1 = title;
            }

            if(lead.isEmpty()){
                lead1 = "-";
            } else {
                lead1 = lead;
            }

            if(date.isEmpty()){
                date1 = "-";
            } else {
                date1 = date;
            }

            movieItems = new MovieItems();
            movieItems.setTitle(title1);
            movieItems.setLead(lead1);
            movieItems.setDate(date1);
            movieItems.setPhoto(photo);
            movieList.add(movieItems);
        }
        return movieList;
    }

    protected void onReleaseResources(ArrayList<MovieItems> data) {
        //nothing to do.
    }
}
