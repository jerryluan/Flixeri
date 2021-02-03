package com.example.movieapp.adpaters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.detailActivity;
import com.example.movieapp.models.movie;

import org.parceler.Parcels;

import java.util.List;

public class movieAdapter extends RecyclerView.Adapter<movieAdapter.viewHolder> {

    Context context;
    List<movie> movies;

    public movieAdapter(Context context, List<movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View moviesView = LayoutInflater.from (context).inflate(R.layout.item_movie,parent,false);
        return new viewHolder(moviesView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        movie mv =  movies.get(position);
        holder.bind(mv);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView mvtitle;
        TextView mvOverview;
        ImageView mvPoster;
        TextView review;
        RelativeLayout container;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            mvtitle = itemView.findViewById(R.id.mvtitle);
            mvOverview = itemView.findViewById(R.id.mv_overview);
            mvPoster = itemView.findViewById(R.id.poster);
            review = itemView.findViewById(R.id.review);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(movie mv) {
            String title = mv.getTitle();
            mvtitle.setText(title);
            mvOverview.setText(mv.getOverview());

            String temp = "";
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                temp = mv.getBackdrop();
            else
                temp = mv.getPpath();
            Glide.with(context).load(temp).into(mvPoster);

            review.setText(String.format("Rating: %f",mv.getReview()));

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, detailActivity.class);
                    i.putExtra("title",title);
                    i.putExtra("mvObj", Parcels.wrap(mv));
                    context.startActivity(i);
                }
            });
        }
    }
}
