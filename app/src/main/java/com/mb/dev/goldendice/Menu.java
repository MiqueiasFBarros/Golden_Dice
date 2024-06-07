package com.mb.dev.goldendice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.theartofdev.edmodo.cropper.CropImage;

import java.util.List;

public class Menu extends AppCompatActivity {

    private static final int REQUEST_CODE_CROP_IMAGE = 1234;

    private ImageHandler imageHandler;
    private DatabaseHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageButton backButton = findViewById(R.id.returnMenu);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        ImageButton photoButton = findViewById(R.id.photoButton);
        SharedPreferences sharedPreferences = getSharedPreferences("MuPreferences", MODE_PRIVATE);

        imageHandler = new ImageHandler(this, photoButton, sharedPreferences);

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageHandler.selectImage();
            }
        });

        db = new DatabaseHelper(this);
        updateResults();
    }

    public void rollDice() {
        int result = 6;
        db.addResult(result, "D6");
        updateResults();
    }

        private void updateResults() {
            List<Result> results = db.getAllResults();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView resultsTextView = findViewById(R.id.resultsTextView);
                    if (results != null) {
                        StringBuilder resultsString = new StringBuilder();
                        for (Result result : results) {
                            resultsString.append(result.toString()).append("\n");
                        }
                        resultsTextView.setText(resultsString.toString());
                    }
                }
            });
        }


    @Override
    public void onResume() {
        super.onResume();
        Bitmap image = imageHandler.loadImage();
        if (image != null) {
            ImageButton photoButton = findViewById(R.id.photoButton);
            photoButton.setImageBitmap(image);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imageHandler.loadImage(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        } else if (requestCode == REQUEST_CODE_CROP_IMAGE && resultCode == RESULT_OK) {
            Uri croppedImageUri = data.getData();
            imageHandler.loadImage(croppedImageUri);
        } else {
            imageHandler.handleActivityResult(requestCode, resultCode, data);
        }
    }
}