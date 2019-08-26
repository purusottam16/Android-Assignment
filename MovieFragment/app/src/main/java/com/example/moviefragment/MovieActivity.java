package com.sanjay.moviefragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import static com.sanjay.moviefragment.MainActivity.SECOND_SCREEN;

public class MovieActivity extends AppCompatActivity {

    Boolean mDualPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        getSupportActionBar().hide();

        View detailFrame = findViewById(R.id.detailFragment);
        mDualPane = detailFrame != null
                && detailFrame.getVisibility() == View.VISIBLE;

        Log.e("MovieActivity","Value "+mDualPane);

        FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.topFragment, TopFragment.newInstance(SECOND_SCREEN,mDualPane));

        fragmentTransaction.add(R.id.centreFragment, MovieListFragment.newInstance(mDualPane));
        if(mDualPane){
            MovieModel movieModel = new MovieModel("Khiladi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.khiladi);
            fragmentTransaction.add(R.id.detailFragment, MovieInformationFragment.newInstance(movieModel,mDualPane));
        }
        fragmentTransaction.add(R.id.bottomFragment, new BottomFragment());

        fragmentTransaction.commit();
    }
}
