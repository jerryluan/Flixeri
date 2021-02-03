package com.example.movieapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.movieapp.models.movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class detailActivity extends YouTubeBaseActivity {
    TextView mt;
    TextView overview;
    RatingBar rb;
    String ytAPI = "AIzaSyAhTPFHdHuZRbhk_UgSfK1Q54ppq1wTGd8";
    YouTubePlayerView ytp;
    String api1 = "https://api.themoviedb.org/3/movie/%s/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    movie movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mt = findViewById(R.id.MVtitle);
        overview = findViewById(R.id.MVoverview);
        rb = findViewById(R.id.ratingBar);

        String title = getIntent().getStringExtra("title");
        movies = Parcels.unwrap(getIntent().getParcelableExtra("mvObj"));
        mt.setText(title);
        rb.setRating(movies.getReview());
        overview.setText(movies.getOverview());
        ytp = findViewById(R.id.player);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(api1,movies.getId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d("detail,get","onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try{
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i("detail,get","Results"+ results.toString());
                    if(results.length()==0)
                        return;

                    int temp = 0;
                    while(temp<results.length()){
                        String temp1 = results.getJSONObject(temp).getString("site");
                        if( temp1.equalsIgnoreCase("YouTube")){
                            Log.d("counter",String.format("%d,%s",temp,temp1));
                            break;
                        }
                        else
                            temp++;
                    }
                    String youtubeKey = results.getJSONObject(temp).getString("key");
                    Log.i("detail",youtubeKey);
                    ytstart(youtubeKey);

                }
                catch (JSONException e){
                    Log.e("detail,get","Hit Json exception",e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d("detail,get","onFailure");
            }
        });


    }

    private void ytstart(final String key){
        ytp.initialize(ytAPI, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("detail","onSucecss");
                youTubePlayer.cueVideo(key);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("detail","YT onfailure");
            }
        });

    }

}