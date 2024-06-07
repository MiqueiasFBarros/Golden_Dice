package com.mb.dev.goldendice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.theartofdev.edmodo.cropper.CropImage;

public class Menu extends AppCompatActivity {

    private static final int REQUEST_CODE_CROP_IMAGE = 1234;

    private ImageHandler imageHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bitmap image = imageHandler.loadImage();
        if (image != null) {
            ImageButton photoButton = findViewById(R.id.photoButton);
            photoButton.setImageBitmap(image);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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