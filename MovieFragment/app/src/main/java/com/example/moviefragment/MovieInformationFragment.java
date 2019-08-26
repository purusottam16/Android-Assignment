package com.sanjay.moviefragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieInformationFragment extends Fragment {

    MovieModel movieModels;

    TextView movieTextView, movieDescView,textView;
    ImageView imageView;
    Button backButton;
    Boolean mDualPane;

    public static MovieInformationFragment newInstance(MovieModel movieModel, Boolean mDualPane) {
        
        Bundle args = new Bundle();
        
        MovieInformationFragment fragment = new MovieInformationFragment();
        args.putParcelable("movieData", movieModel);
        args.putBoolean("dualPane",mDualPane);
        fragment.setArguments(args);
        return fragment;
    }


    public MovieInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_information, container, false);
        backButton = view.findViewById(R.id.backButton);

        mDualPane = getArguments().getBoolean("dualPane");
        movieModels = getArguments().getParcelable("movieData");

        if (movieModels != null) {

            movieTextView = view.findViewById(R.id.movieTextView);
            movieDescView = view.findViewById(R.id.movieDescView);
            imageView = view.findViewById(R.id.imageView);

            movieTextView.setText(movieModels.MovieTitle);
            movieDescView.setText(movieModels.MovieDescription);
            imageView.setImageDrawable(getContext().getResources().getDrawable(movieModels.getMovieImage()));

        } else {

        }

        return view;
    }


}
