package com.thor.submission2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thor.submission2.DetailActivity;
import com.thor.submission2.Movie;
import com.thor.submission2.R;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private Context context;
    private ArrayList<Movie> listMovie = new ArrayList<>();


    public MovieAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_list,viewGroup,false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder movieAdapterViewHolder, final int i) {


        movieAdapterViewHolder.tvTitle.setText(listMovie.get(i).getTitle());
        movieAdapterViewHolder.tvOverview.setText(listMovie.get(i).getOverview());

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185"+listMovie.get(i).getPosterPath())
                .apply(new RequestOptions().override(80,100))
                .into(movieAdapterViewHolder.imgPoster);

        movieAdapterViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_MOVIE,listMovie.get(i));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return getListMovie().size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle;
        TextView tvOverview;
        LinearLayout linearLayout;
        public MovieAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_judul);
            tvOverview = itemView.findViewById(R.id.tv_sinposis);
            linearLayout = itemView.findViewById(R.id.ll_parent);
        }
    }
}
