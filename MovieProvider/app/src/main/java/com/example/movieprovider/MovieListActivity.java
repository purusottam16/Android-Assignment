package com.sanjay.movieprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MovieListActivity extends AppCompatActivity {

    ArrayList<MovieModel> movieModels = new ArrayList<>();
    RecyclerView recyclerView;
    MovieProviderUtility movieProviderUtility;

    SwipeRefreshLayout swipeRefreshLayout;

    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        movieProviderUtility =  new MovieProviderUtility(getApplicationContext());

        movieModels = movieProviderUtility.ShowAllMovies();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeReferesh);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieAdapter = new MovieAdapter(movieModels,movieProviderUtility, getApplicationContext(),swipeRefreshLayout);
        recyclerView.setAdapter(movieAdapter);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        movieAdapter.onActivityResult(requestCode,resultCode,data);
    }
}
