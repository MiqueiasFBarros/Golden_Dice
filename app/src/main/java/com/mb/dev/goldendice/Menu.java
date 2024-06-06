package com.mb.dev.goldendice;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Menu extends AppCompatActivity {

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageHandler.handleActivityResult(requestCode, resultCode, data);
    }
}