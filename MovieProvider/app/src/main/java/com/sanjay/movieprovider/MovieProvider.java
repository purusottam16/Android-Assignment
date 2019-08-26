package com.sanjay.movieprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

public class MovieProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.sanjay.movieprovider.provider";
    static final String URL = "content://" + PROVIDER_NAME + "/movies";
    static final Uri CONTENT_URI = Uri.parse(URL);

    //Database fields
    static final String MovieID = "movieId";
    static final String MovieTitle = "MovieTitle";
    static final String MovieDesc = "MovieDesc";
    static final String MovieImage = "MovieImage";

    // integer values used in content URI
    static final int MOVIES = 1;
    static final int MOVIES_ID = 2;

    private static HashMap<String, String> MovieMap;

    MovieDBHelper movieDBHelper;
    private SQLiteDatabase database;
    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "movies", MOVIES);
        uriMatcher.addURI(PROVIDER_NAME, "movies/#", MOVIES_ID);
    }

    @Override
    public boolean onCreate() {
        // TODO Auto-generated method stub
        Context context = getContext();
        movieDBHelper = new MovieDBHelper(context);
        // permissions to be writable
        database = movieDBHelper.getWritableDatabase();

        if(database == null)
            return false;
        else
            return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        // the TABLE_NAME to query on
        queryBuilder.setTables(movieDBHelper.TABLE_MOVIE);

        switch (uriMatcher.match(uri)) {
            // maps all database column names
            case MOVIES:
                queryBuilder.setProjectionMap(MovieMap);
                break;
            case MOVIES_ID:
                queryBuilder.appendWhere( MovieID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == ""){
            // No sorting-> sort on names by default
            sortOrder = MovieTitle;
        }
        Cursor cursor = queryBuilder.query(database, projection, selection,
                selectionArgs, null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            // Get all friend-birthday records
            case MOVIES:
                return "vnd.android.cursor.dir/vnd.sanjay.movies";
            // Get a particular friend
            case MOVIES_ID:
                return "vnd.android.cursor.item/vnd.sanjay.movies";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long row = database.insert(movieDBHelper.TABLE_MOVIE, "", values);
        // If record is added successfully
        if(row > 0) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Fail to add a new record into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case MOVIES:
                // delete all the records of the table
                count = database.delete(movieDBHelper.TABLE_MOVIE, selection, selectionArgs);
                break;
            case MOVIES_ID:
                String id = uri.getLastPathSegment(); //gets the id
                count = database.delete( movieDBHelper.TABLE_MOVIE, MovieID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        int count = 0;

        switch (uriMatcher.match(uri)){
            case MOVIES:
                count = database.update(movieDBHelper.TABLE_MOVIE, values, selection, selectionArgs);
                break;
            case MOVIES_ID:
                count = database.update(movieDBHelper.TABLE_MOVIE, values, MovieID +
                        " = " + uri.getLastPathSegment() +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}

