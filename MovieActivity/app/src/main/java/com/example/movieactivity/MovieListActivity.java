package com.example.movieactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    TextView textView;

    ArrayList<MovieModel> movieModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

       /* String[] data ={"Khiladi","Mein Khiladi tu Anadi","Sabse bada Khiladi","Khiladiyo ka Khiladi","International Khiladi"
                        ,"Khiladi 420","Janlewa Khiladi","Dabangg Khiladi","Timepass Khiladi","Ye kaisa Khiladi","Shit Khiladi","Bakwaas Khiladi","Darpok Khiladi"};


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(data, getApplicationContext()));*/

        movieModels.add(new MovieModel("Khiladi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.khiladi));
        movieModels.add(new MovieModel("Mein Khiladi tu Anadi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.mainkhiladituanadi));
        movieModels.add(new MovieModel("Sabse bada Khiladi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.sabsebada_khiladi));
        movieModels.add(new MovieModel("Khiladiyo ka Khiladi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.khatronke_khiladi));
        movieModels.add(new MovieModel("International Khiladi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.international_khiladi));
        movieModels.add(new MovieModel("Khiladi 420","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.khiladi_420));
        movieModels.add(new MovieModel("Janlewa Khiladi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.khatronke_khiladi));
        movieModels.add(new MovieModel("Dabangg Khiladi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.shit_khiladi));
        movieModels.add(new MovieModel("Timepass Khiladi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.khiladi_786));
        movieModels.add(new MovieModel("Ye kaisa Khiladi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.dangerous_khiladi));
        movieModels.add(new MovieModel("Shit Khiladi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.sabsebada_khiladi));
        movieModels.add(new MovieModel("Bakwaas Khiladi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.shit_khiladi));


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MovieAdapter(movieModels, getApplicationContext()));


    }

}




