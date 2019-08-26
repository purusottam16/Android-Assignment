package com.example.movieactivity;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieModel implements Parcelable {
    public String MovieTitle;
    public String MovieDescription;
    public String MovieCast;
    public String MovieDirector;
    public int MovieImage;

    public MovieModel(String MovieTitle,String MovieDescription,String MovieCast,String MovieDirector,int MovieImage){
        this.MovieTitle = MovieTitle;
        this.MovieDescription = MovieDescription;
        this.MovieCast = MovieCast;
        this.MovieDirector = MovieDirector;
        this.MovieImage = MovieImage;
    }

    protected MovieModel(Parcel in) {
        MovieTitle = in.readString();
        MovieDescription = in.readString();
        MovieCast = in.readString();
        MovieDirector = in.readString();
        MovieImage = in.readInt();
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
        dest.writeString(MovieTitle);
        dest.writeString(MovieDescription);
        dest.writeString(MovieCast);
        dest.writeString(MovieDirector);
        dest.writeInt(MovieImage);
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

    public String getMovieCast() {
        return MovieCast;
    }

    public void setMovieCast(String movieCast) {
        MovieCast = movieCast;
    }

    public String getMovieDirector() {
        return MovieDirector;
    }

    public void setMovieDirector(String movieDirector) {
        MovieDirector = movieDirector;
    }

    public int getMovieImage() {
        return MovieImage;
    }

    public void setMovieImage(int movieImage) {
        MovieImage = movieImage;
    }
}
