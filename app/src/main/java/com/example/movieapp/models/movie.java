package com.example.movieapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
@Parcel
public class movie {

    String ppath;
    String title;
    String overview;
    Double review;
    String backdrop;
    int id;

    public movie(JSONObject jo) throws JSONException {
        ppath = "https://image.tmdb.org/t/p/w342"+jo.getString("poster_path");
        title = jo.getString("title");
        overview = jo.getString("overview");
        review = jo.getDouble("vote_average");
        backdrop = jo.getString("backdrop_path");
        id = jo.getInt("id");
    }

    //Empty constructor for parcel
    public movie(){}


    public static List<movie> fromJsonArray(JSONArray movieArray) throws JSONException {
        List<movie> movies = new ArrayList<>();
        for(int i = 0; i < movieArray.length();i++){
            movies.add(new movie(movieArray.getJSONObject(i)));
        }
        return movies;
    }

    public int getId() {
        return id;
    }

    public float getReview() {
        return review.floatValue();
    }

    public String getBackdrop() {
        return "https://image.tmdb.org/t/p/w342"+backdrop;
    }

    public String getPpath() {
        return ppath;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
