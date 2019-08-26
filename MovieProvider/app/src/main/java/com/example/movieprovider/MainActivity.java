package com.sanjay.movieprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Button viewButton, saveButton, selectImageButton;
    public ImageView imageDialogView;
    ArrayList<MovieModel> movieModels = new ArrayList<>();
    TextInputEditText editText,editDesc;
    private Uri selectedImageUri;
    public static final int SELECT_PICTURE = 100;

    MovieProviderUtility movieProviderUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieProviderUtility =  new MovieProviderUtility(getApplicationContext());

        viewButton = findViewById(R.id.button);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        viewButton.setOnClickListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog();
            }
        });
    }

    public void displayDialog() {
        final Dialog d = new Dialog(this);
        d.setTitle("Save To Movie Database");
        d.setContentView(R.layout.dialog_input);
        editText = (TextInputEditText) d.findViewById(R.id.editText);
        editDesc = (TextInputEditText) d.findViewById(R.id.editDesc);
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
                        iStream = getContentResolver().openInputStream(selectedImageUri);
                        inputData = MovieImageUtility.getBytes(iStream);
                        inputData = MovieImageUtility.getImageBytes(MovieImageUtility.getImage(inputData));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    movieProviderUtility.AddMovie(editTextValue,editDescValue,inputData);
                    //  movieModels = movieProviderUtility.ShowAllMovies();

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
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    imageDialogView.setImageURI(selectedImageUri);
                    imageDialogView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        movieModels = movieProviderUtility.ShowAllMovies();

        Intent intent = new Intent(this,MovieListActivity.class);
        startActivity(intent);
    }
}
