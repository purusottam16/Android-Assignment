package com.example.movieactivity;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieInformationActivity extends AppCompatActivity {

    TextView movieTextView, movieDescView,textView;
    ImageView imageView;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);

       /*String buttonText = getIntent().getStringExtra("buttonText");
        textView = findViewById(R.id.movieTextView);
        textView.setText(buttonText);*/

       MovieModel movieModel = getIntent().getParcelableExtra("movieData");
        movieTextView = findViewById(R.id.movieTextView);
        movieDescView = findViewById(R.id.movieDescView);
        imageView = findViewById(R.id.imageView);

        movieTextView.setText(movieModel.MovieTitle);
        movieDescView.setText(movieModel.MovieDescription);
        imageView.setImageDrawable(getApplicationContext().getDrawable(movieModel.getMovieImage()));

    }
}
