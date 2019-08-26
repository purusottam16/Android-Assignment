package com.sanjay.moviefragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int MAIN_SCREEN = 1;
    public static final int SECOND_SCREEN = 2;

    Button movieButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.topFragment, TopFragment.newInstance(MAIN_SCREEN,false));
        fragmentTransaction.add(R.id.bottomFragment, new BottomFragment());

        fragmentTransaction.commit();

        movieButton = findViewById(R.id.movieButton);
        movieButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MovieActivity.class);
        startActivity(intent);
    }
}
