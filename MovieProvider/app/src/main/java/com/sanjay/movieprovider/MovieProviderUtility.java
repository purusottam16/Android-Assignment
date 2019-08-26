package com.sanjay.movieprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MovieProviderUtility {

    //  public Context real_context;
    public Context context;

    public void MovieProviderUtility(Context context){

        this.context = context;

    }

    public MovieProviderUtility(Context context){

        this.context = context;

    }

    public void DeleteAllMovies () {
        // Delete all the records
        String URL = "content://com.sanjay.movieprovider.provider/movies";
        Uri movies = Uri.parse(URL);
        int count = context.getContentResolver().delete(
                movies, null, null);
        String countNum = "Movies: "+ count +" records are deleted.";

    }

    public void AddMovie(String MovieTitle, String MovieDesc,byte[] imageBytes) {
        // Add a new movie
        ContentValues values = new ContentValues();

        values.put(MovieProvider.MovieTitle,MovieTitle);
        values.put(MovieProvider.MovieDesc,MovieDesc);
        if(imageBytes != null) {
            values.put(MovieProvider.MovieImage, imageBytes);
        }
        Uri uri = context.getContentResolver().insert(MovieProvider.CONTENT_URI, values);

    }

    public void UpdateMovie(int movieId,  String MovieTitle, String MovieDesc,byte[] imageBytes) {
        // Update an movie
        ContentValues values = new ContentValues();

        values.put(MovieProvider.MovieTitle,MovieTitle);
        values.put(MovieProvider.MovieDesc,MovieDesc);
        if(imageBytes != null) {
            values.put(MovieProvider.MovieImage, imageBytes);
        }
        String whereClause = "movieId = ?";
        String whereArgs[] = new String[]{String.valueOf(movieId)};

        Integer row = context.getContentResolver().update(MovieProvider.CONTENT_URI, values, whereClause, whereArgs);
    }

    public void DeleteMovie(int movieId) {

        String whereClause = "movieId = ?";
        String whereArgs[] = new String[]{String.valueOf(movieId)};

        Integer row = context.getContentResolver().delete(MovieProvider.CONTENT_URI,whereClause, whereArgs);
    }


    public ArrayList<MovieModel> ShowAllMovies() {
        // Show all the Movies
        ArrayList<MovieModel> moviesColl = new ArrayList<>();

        String URL = "content://com.sanjay.movieprovider.provider/movies";
        Uri movies = Uri.parse(URL);
        Cursor cursor = context.getContentResolver().query(movies, null, null, null, "MovieTitle");
        String result = "Movie Results:";

        if(cursor.moveToFirst()){

            do {

                MovieModel movie = new MovieModel();
                movie.mId =  cursor.getInt(cursor.getColumnIndex("movieId"));
                movie.MovieTitle = cursor.getString(cursor.getColumnIndex("MovieTitle"));
                movie.MovieDescription = cursor.getString(cursor.getColumnIndex("MovieDesc"));
                movie.MovieImage = cursor.getBlob(cursor.getColumnIndex("MovieImage"));
                moviesColl.add(movie);

               // cursor.moveToNext();
            } while (cursor.moveToNext());
        }

        return moviesColl;

    }
}
