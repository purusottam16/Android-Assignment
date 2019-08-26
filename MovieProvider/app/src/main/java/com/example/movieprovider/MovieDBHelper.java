package com.sanjay.movieprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MovieDBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "MoviesProvider";
    public static  final int DBVERSION = 1;

    public static final String TABLE_MOVIE = "MoviesTableContent";

    public static final String CREATE_TABLE_MOVIE = " CREATE TABLE "+ TABLE_MOVIE +"(movieId INTEGER PRIMARY KEY, MovieTitle TEXT, MovieDesc TEXT,MovieImage BLOB)";

    public MovieDBHelper(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
