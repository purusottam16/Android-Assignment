package com.sanjay.moviesqlite;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;


public class MovieModel implements Parcelable {
    public int mId;
    public String MovieTitle;
    public String MovieDescription;
    public byte[] MovieImage;

    public MovieModel(){

    }

    public MovieModel(String MovieTitle,String MovieDescription,int mId,byte[] MovieImage){
        this.MovieTitle = MovieTitle;
        this.MovieDescription = MovieDescription;
        this.mId = mId;
        this.MovieImage = MovieImage;
    }


    protected MovieModel(Parcel in) {
        mId = in.readInt();
        MovieTitle = in.readString();
        MovieDescription = in.readString();
        MovieImage = in.createByteArray();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(MovieTitle);
        dest.writeString(MovieDescription);
        dest.writeByteArray(MovieImage);
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getMovieTitle() {
        return MovieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        MovieTitle = movieTitle;
    }

    public String getMovieDescription() {
        return MovieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        MovieDescription = movieDescription;
    }

    public byte[] getMovieImage() {
        return MovieImage;
    }

    public void setMovieImage(byte[] movieImage) {
        MovieImage = movieImage;
    }
}
