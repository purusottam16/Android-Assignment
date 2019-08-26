package com.sanjay.movieprovider;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public ImageView imageDialogView;
    // public TextView textView, descView;
    public TextInputEditText editText,editDesc;
    public Context real_context;
    Context context;
    public Button updateButton, deleteButton,saveButton,selectImageButton;

    public int currentSelectedPosition = RecyclerView.NO_POSITION;
    private Uri selectedImageUri;

    ArrayList<MovieModel> movieModels;
    MovieProviderUtility movieProviderUtility;

    ArrayList<MovieModel> movieModelsDB = new ArrayList<>();

    SwipeRefreshLayout swipeRefreshLayout;

    public MovieAdapter(ArrayList<MovieModel> movieModels,MovieProviderUtility movieProviderUtility, Context context,SwipeRefreshLayout swipeRefreshLayout){

        this.movieModels = movieModels;
        this.context = context;
        this.movieProviderUtility = movieProviderUtility;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    public class MovieItem extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView, descView;

        public MovieItem(@NonNull View itemView) {
            super(itemView);
            real_context = itemView.getContext();
            movieProviderUtility = new MovieProviderUtility(real_context);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            descView = itemView.findViewById(R.id.descView);

            updateButton = itemView.findViewById(R.id.updateButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
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
                descView.setText(movie.MovieDescription);
                if(movie.MovieImage != null) {
                    imageView.setImageBitmap(MovieImageUtility.getImage(movie.MovieImage));
                }
                //  imageView.setImageDrawable(real_context.getResources().getDrawable(movie.getMovieImage()));
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_view,viewGroup,false);
        return new MovieItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        ((MovieItem)viewHolder).configureItem();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                movieModels = movieProviderUtility.ShowAllMovies();

                swipeRefreshLayout.setRefreshing(false);
                notifyDataSetChanged();

                viewHolder.itemView.findViewById(R.id.cardMovie).setBackgroundColor(Color.WHITE);
                viewHolder.itemView.findViewById(R.id.updateButton).setVisibility(View.GONE);
                viewHolder.itemView.findViewById(R.id.deleteButton).setVisibility(View.GONE);
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSelectedPosition = position ;
                notifyDataSetChanged();
            }
        });

        if (currentSelectedPosition == position) {

            final MovieModel movieModel = ((MovieItem)viewHolder).getData();

            viewHolder.itemView.findViewById(R.id.cardMovie).setBackgroundColor(Color.CYAN);

            viewHolder.itemView.findViewById(R.id.updateButton).setVisibility(View.VISIBLE);
            viewHolder.itemView.findViewById(R.id.updateButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(movieModel != null){
                        displayDialog(movieModel);
                    }
                    viewHolder.itemView.findViewById(R.id.updateButton).setVisibility(View.GONE);
                    viewHolder.itemView.findViewById(R.id.deleteButton).setVisibility(View.GONE);
                }
            });

            viewHolder.itemView.findViewById(R.id.deleteButton).setVisibility(View.VISIBLE);
            viewHolder.itemView.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  db.deleteMovie(movieModel.mId);
                    movieProviderUtility.DeleteMovie(movieModel.mId);

                    viewHolder.itemView.findViewById(R.id.cardMovie).setBackgroundColor(Color.WHITE);
                    viewHolder.itemView.findViewById(R.id.updateButton).setVisibility(View.GONE);
                    viewHolder.itemView.findViewById(R.id.deleteButton).setVisibility(View.GONE);

                }
            });

        } else {
            viewHolder.itemView.findViewById(R.id.cardMovie).setBackgroundColor(Color.WHITE);
            viewHolder.itemView.findViewById(R.id.updateButton).setVisibility(View.GONE);
            viewHolder.itemView.findViewById(R.id.deleteButton).setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return movieModels.size();
    }

    private void displayDialog(final MovieModel mv) {

        final Dialog d = new Dialog(real_context);
        d.setTitle("Save To Movie Database");
        d.setContentView(R.layout.dialog_input);
        editText = (TextInputEditText) d.findViewById(R.id.editText);
        editText.setText(mv.MovieTitle);
        editDesc = (TextInputEditText) d.findViewById(R.id.editDesc);
        editDesc.setText(mv.MovieDescription);
        imageDialogView = d.findViewById(R.id.imageDialogView);
        selectImageButton = d.findViewById(R.id.selectImageButton);


        saveButton = (Button) d.findViewById(R.id.saveButton);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editTextValue = editText.getText().toString();
                String editDescValue = editDesc.getText().toString();

                if (editTextValue.length() > 0 && editDescValue.length() > 0 && selectedImageUri != null) {
                    InputStream iStream = null;
                    byte[] inputData = null;
                    try {
                        iStream = real_context.getContentResolver().openInputStream(selectedImageUri);
                        inputData = MovieImageUtility.getBytes(iStream);
                        inputData = MovieImageUtility.getImageBytes(MovieImageUtility.getImage(inputData));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    movieProviderUtility.UpdateMovie(mv.mId,editTextValue,editDescValue,inputData);
                    movieModels = movieProviderUtility.ShowAllMovies();

                    d.findViewById(R.id.textinput_error).setVisibility(View.GONE);
                    d.dismiss();
                } else {
                    d.findViewById(R.id.textinput_error).setVisibility(View.VISIBLE);
                }
            }
        });
        d.show();
    }

    public void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        ((MovieListActivity)real_context).startActivityForResult(Intent.createChooser(intent, "Select Picture"), MainActivity.SELECT_PICTURE);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == MainActivity.SELECT_PICTURE) {
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    imageDialogView.setImageURI(selectedImageUri);
                    imageDialogView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

}

