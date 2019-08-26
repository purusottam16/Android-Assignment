package com.example.movieactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public TextView textViewMain;
    public ImageView imageViewMain;
    public Button viewButton;
    static  final int REQUEST_SECOND = 400;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewButton = findViewById(R.id.button);
        viewButton.setOnClickListener(this);

      //  LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("SendMovie"));
    }

    public void TriggerIntent(View view)
    {
        Intent i = new Intent(this,MovieListActivity.class);
        startActivity(i);
    }
    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, MovieListActivity.class);
      //  startActivity(intent);
        startActivityForResult(intent,REQUEST_SECOND);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_SECOND && resultCode == RESULT_OK){

          //  int value = data.getIntExtra("result",-1);
            findViewById(R.id.cardMovieMain).setVisibility(View.VISIBLE);

           // String title = data.getStringExtra("movieData");
            MovieModel movieModel = data.getParcelableExtra("movieData");
            Log.e("MovieActivity1","value" +movieModel.MovieTitle);

            textViewMain = findViewById(R.id.textViewMain);
            imageViewMain = findViewById(R.id.imageViewMain);

            textViewMain.setText(movieModel.MovieTitle);
            imageViewMain.setImageDrawable(getApplicationContext().getDrawable(movieModel.getMovieImage()));

            Log.e("MovieActivity2","value" +movieModel.MovieTitle);
        }
    }


}
