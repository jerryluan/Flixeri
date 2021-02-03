package com.example.movieapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.movieapp.adpaters.movieAdapter;
import com.example.movieapp.models.movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String apiUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String tagMain = "mainActivity";
    List<movie> movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movies = new ArrayList<>();
        RecyclerView rvMovies = findViewById(R.id.rvMovie);

        movieAdapter ma = new movieAdapter(this,movies);
        rvMovies.setAdapter(ma);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(apiUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(tagMain,"onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try{
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(tagMain,"Results"+ results.toString());
                    movies.addAll(movie.fromJsonArray(results));
                    ma.notifyDataSetChanged();

                    Log.i(tagMain,String.format("movies size is %d",movies.size()));
                }
                catch (JSONException e){
                    Log.e(tagMain,"Hit Json exception",e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(tagMain,"onFailure");
            }
        });

    }
}