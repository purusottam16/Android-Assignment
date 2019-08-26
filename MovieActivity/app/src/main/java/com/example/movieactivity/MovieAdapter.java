package com.example.movieactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ImageView imageView;
    public TextView textView;
    public Context real_context;
    Context context;
    public Button selectButton, cancelButton;

    public int currentSelectedPosition = RecyclerView.NO_POSITION;

    ArrayList<MovieModel> movieModels;

    public MovieAdapter(ArrayList<MovieModel> movieModels, Context context){
        this.movieModels = movieModels;
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
            selectButton = itemView.findViewById(R.id.selectButton);
            cancelButton = itemView.findViewById(R.id.cancelButton);

            textView.setOnClickListener(this);
            imageView.setOnClickListener(this);
        }

        private MovieModel getData(){
            if(getAdapterPosition() >=0 && getAdapterPosition() < movieModels.size())
                return movieModels.get(getAdapterPosition());
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
            if(movieModel != null){
                Intent intent = new Intent(real_context,MovieInformationActivity.class);
                intent.putExtra("movieData",movieModel);
                real_context.startActivity(intent);
            }

        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_view,viewGroup,false);
        return new MovieItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        ((MovieItem)viewHolder).configureItem();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSelectedPosition = position ;
                notifyDataSetChanged();
            }
        });
        if (currentSelectedPosition == position) {
            viewHolder.itemView.findViewById(R.id.cardMovie).setBackgroundColor(Color.CYAN);
            viewHolder.itemView.findViewById(R.id.selectButton).setVisibility(View.VISIBLE);
            viewHolder.itemView.findViewById(R.id.selectButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MovieModel movieModel = ((MovieItem)viewHolder).getData();
                    if(movieModel != null){

                        Intent intent = new Intent();
                        intent.putExtra("movieData",movieModel);
                        ((Activity) real_context).setResult(RESULT_OK,intent);

                        ((Activity) real_context).finish();

                      /*  Log.e("MovieAdapter","value" +movieModel.MovieTitle);

                        Intent intent = new Intent("SendMovie");
                        intent.putExtra("movieData",movieModel.MovieTitle);
                        LocalBroadcastManager.getInstance(real_context).sendBroadcast(intent);

                        Intent intent1 = new Intent(real_context,MainActivity.class);
                        //  intent.putExtra("movieData",movieModel);
                        real_context.startActivity(intent1);*/

                    }
                }
            });
            viewHolder.itemView.findViewById(R.id.cancelButton).setVisibility(View.VISIBLE);
            viewHolder.itemView.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewHolder.itemView.findViewById(R.id.cardMovie).setBackgroundColor(Color.WHITE);
                    viewHolder.itemView.findViewById(R.id.selectButton).setVisibility(View.GONE);
                    viewHolder.itemView.findViewById(R.id.cancelButton).setVisibility(View.GONE);

                }
            });
        } else {
            viewHolder.itemView.findViewById(R.id.cardMovie).setBackgroundColor(Color.WHITE);
            viewHolder.itemView.findViewById(R.id.selectButton).setVisibility(View.GONE);
            viewHolder.itemView.findViewById(R.id.cancelButton).setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return movieModels.size();
    }
}
