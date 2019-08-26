package com.sanjay.moviesqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MovieDB extends SQLiteOpenHelper {

    private static final String DBNAME = "movies.db";
    private static  final int DBVERSION = 2;

    private static final String TABLE_MOVIE = "MoviesTable";

    private static final String CREATE_TABLE_MOVIE = " CREATE TABLE "+ TABLE_MOVIE +"(movieId INTEGER PRIMARY KEY, MovieTitle TEXT, MovieDesc TEXT,MovieImage BLOB)";

    private static final String ALTER_TABLE_MOVIE = " ALTER TABLE "+ TABLE_MOVIE +" ADD COLUMN MovieImage BLOB";

    public MovieDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DBNAME, factory, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE_MOVIE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
       /* if(i1 == i + 1) {
            sqLiteDatabase.execSQL(ALTER_TABLE_MOVIE);
        }*/
    }

    boolean insertMovie(String MovieTitle, String MovieDesc,byte[] imageBytes){
        ContentValues values = new ContentValues();
      //  values.put("mId",mId);
        values.put("MovieTitle",MovieTitle);
        values.put("MovieDesc",MovieDesc);
        if(imageBytes != null) {
            values.put("MovieImage", imageBytes);
        }

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        long rowId = sqLiteDatabase.insert(TABLE_MOVIE, null,values);

        sqLiteDatabase.close();

        return rowId > 0;
    }

    boolean updateMovie(int movieId, String MovieTitle, String MovieDesc,byte[] imageBytes){
        ContentValues values = new ContentValues();
        values.put("MovieTitle",MovieTitle);
        values.put("MovieDesc",MovieDesc);
        if(imageBytes != null) {
            values.put("MovieImage", imageBytes);
        }


        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String whereClause = "movieId = ?";
        String whereArgs[] = new String[]{String.valueOf(movieId)};

        long rowId = sqLiteDatabase.update(TABLE_MOVIE,values,whereClause,whereArgs);

        sqLiteDatabase.close();

        return rowId > 0;

    }


    boolean deleteMovie(int movieId){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String whereClause = "movieId = ?";
        String whereArgs[] = new String[]{String.valueOf(movieId)};

        long rowId = sqLiteDatabase.delete(TABLE_MOVIE,whereClause,whereArgs);

        sqLiteDatabase.close();

        return rowId > 0;

    }

    ArrayList<MovieModel> getAllMovies(){
        ArrayList<MovieModel> movies = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase =  getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM MoviesTable",null);

        if(cursor.moveToFirst()){

            while (!cursor.isAfterLast()){
                MovieModel movie = new MovieModel();
                movie.mId =  cursor.getInt(cursor.getColumnIndex("movieId"));
                movie.MovieTitle = cursor.getString(cursor.getColumnIndex("MovieTitle"));
                movie.MovieDescription = cursor.getString(cursor.getColumnIndex("MovieDesc"));
                movie.MovieImage = cursor.getBlob(cursor.getColumnIndex("MovieImage"));
                movies.add(movie);

                cursor.moveToNext();
            }
        }

        cursor.close();
        sqLiteDatabase.close();

        return movies;
    }
}
