package com.thor.submission2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    ImageView imageViewPoster;
    TextView textViewTitle;
    TextView textViewSinopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageViewPoster = findViewById(R.id.det_img_poster);
        textViewSinopsis = findViewById(R.id.det_tv_sinopsis);
        textViewTitle = findViewById(R.id.det_tv_judul);
        Movie movieItem = getIntent().getExtras().getParcelable(EXTRA_MOVIE);
        String title = movieItem.getTitle();
        String sinopsis = movieItem.getOverview();
        String posterPath = movieItem.getPosterPath();

        textViewTitle.setText(title);
        textViewSinopsis.setText(sinopsis);
        if (posterPath!=null){
            Glide.with(this).load("https://image.tmdb.org/t/p/w185/"+posterPath).into(imageViewPoster);
        }else {
            imageViewPoster.setImageResource(R.drawable.ic_launcher_background);
        }
    }
}
