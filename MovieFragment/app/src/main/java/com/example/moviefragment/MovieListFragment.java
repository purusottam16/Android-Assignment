package com.sanjay.moviefragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<MovieModel> movieModels;
    Boolean mDualPane;

    public static MovieListFragment newInstance(Boolean mDualPane) {
        
        Bundle args = new Bundle();
        
        MovieListFragment fragment = new MovieListFragment();
        args.putBoolean("dualPane",mDualPane);
        fragment.setArguments(args);
        return fragment;
    }


    public MovieListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        movieModels = SetMovieData();
        mDualPane = getArguments().getBoolean("dualPane");

        Log.e("MovieListFragment","Value "+mDualPane);

        recyclerView = view.findViewById(R.id.movieRecycler);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new MovieAdapter(movieModels, this.getContext()));

        return view;
    }

    public ArrayList<MovieModel> SetMovieData(){

        ArrayList<MovieModel> movieData = new ArrayList<>();

        movieData.add(new MovieModel("Khiladi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.khiladi));
        movieData.add(new MovieModel("Mein Khiladi tu Anadi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.mainkhiladituanadi));
        movieData.add(new MovieModel("Sabse bada Khiladi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.sabsebada_khiladi));
        movieData.add(new MovieModel("Khiladiyo ka Khiladi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.khatronke_khiladi));
        movieData.add(new MovieModel("International Khiladi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.international_khiladi));
        movieData.add(new MovieModel("Khiladi 420","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.khiladi_420));
        movieData.add(new MovieModel("Janlewa Khiladi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.khatronke_khiladi));
        movieData.add(new MovieModel("Dabangg Khiladi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.shit_khiladi));
        movieData.add(new MovieModel("Timepass Khiladi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.khiladi_786));
        movieData.add(new MovieModel("Ye kaisa Khiladi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.dangerous_khiladi));
        movieData.add(new MovieModel("Shit Khiladi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.sabsebada_khiladi));
        movieData.add(new MovieModel("Bakwaas Khiladi","This is not Khiladi","Akshay Kumar","Anand Kumar",R.drawable.shit_khiladi));

        return  movieData;
    }



public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public ImageView imageView;
    public TextView textView;
    public Context real_context;
    Context context;

    ArrayList<MovieModel> movieModelData;

    public MovieAdapter(ArrayList<MovieModel> movieModel, Context context){
        this.movieModelData = movieModel;
        this.context = context;
    }

    public class MovieItem extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView textView;

        public MovieItem(@NonNull View itemView) {
            super(itemView);
            real_context = itemView.getContext();

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);

            textView.setOnClickListener(this);
            imageView.setOnClickListener(this);
        }

        private MovieModel getData(){
            if(getAdapterPosition() >=0 && getAdapterPosition() < movieModelData.size())
                return movieModelData.get(getAdapterPosition());
            else
                return null;
        }

        void configureItem(){
            MovieModel movie = getData();
            if(movie != null) {
                textView.setText(movie.MovieTitle);
                imageView.setImageDrawable(real_context.getResources().getDrawable(movie.getMovieImage()));
            }
        }

        @Override
        public void onClick(View v) {
            MovieModel movieModel = getData();
           // Log.e("Movie  ",movieModel.MovieTitle);
            if(movieModel != null){
              //  Log.e("Movie  ",movieModel.MovieTitle);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                if(!mDualPane) {
                    fragmentTransaction.replace(R.id.centreFragment, MovieInformationFragment.newInstance(movieModel, mDualPane));
                }else{
                    fragmentTransaction.replace(R.id.detailFragment, MovieInformationFragment.newInstance(movieModel, mDualPane));
                }
                fragmentTransaction.addToBackStack("Movie_list");
                fragmentTransaction.commit();
            }

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);
        return new MovieItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MovieItem)holder).configureItem();
    }

    @Override
    public int getItemCount() {
        return movieModelData.size();
    }
}

}
