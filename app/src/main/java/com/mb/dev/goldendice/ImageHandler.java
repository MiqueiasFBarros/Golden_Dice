package com.mb.dev.goldendice;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ImageButton;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageHandler {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageButton photoButton;
    private SharedPreferences sharedPreferences;
    private Activity activity;

    public ImageHandler(Activity activity, ImageButton photoButton, SharedPreferences sharedPreferences) {
        this.activity = activity;
        this.photoButton = photoButton;
        this.sharedPreferences = sharedPreferences;
    }

    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public Bitmap loadImage() {
        String encodedImage = sharedPreferences.getString("profileImage", null);
        if (encodedImage != null) {
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
        return null;
    }

    public void loadImage(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
            photoButton.setImageBitmap(bitmap);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            sharedPreferences.edit().putString("profileImage", encodedImage).apply();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImage = data.getData();

            int pixels = (int) (250 * activity.getResources().getDisplayMetrics().density);

            CropImage.activity(selectedImage)
                    .setMinCropResultSize(pixels, pixels)
                    .setMaxCropResultSize(pixels, pixels)
                    .start(activity);
        }
    }

}