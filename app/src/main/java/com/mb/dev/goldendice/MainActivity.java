package com.mb.dev.goldendice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String selectedOption;
    private ImageButton openMenu;
    private SharedPreferences sharedPreferences;
    private String encodedImage;
    private ImageHandler imageHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openMenu = findViewById(R.id.openMenu);
        Spinner spinnerMain = findViewById(R.id.spinner);

        sharedPreferences = getSharedPreferences("MuPreferences", MODE_PRIVATE);
        encodedImage = sharedPreferences.getString("profileImage", null);

        if (encodedImage != null) {
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            openMenu.setImageBitmap(decodedByte);
        }

        imageHandler = new ImageHandler(this, openMenu, sharedPreferences);

        List<String> options = new ArrayList<>();
        options.add("D4");
        options.add("D6");
        options.add("D8");
        options.add("D10");
        options.add("D12");
        options.add("D20");
        options.add("D100");
        options.add("D%");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerMain.setAdapter(adapter);
        spinnerMain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOption = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ImageButton openD20DButton = findViewById(R.id.openD20DButton);
        openD20DButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(selectedOption);
            }
        });

        openMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Menu.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bitmap image = imageHandler.loadImage();
        if (image != null) {
            openMenu.setImageBitmap(image);
        }
    }

    private void openActivity(String selectedOption) {
        Class<?> activityClass;
        int diceSides;
        switch (selectedOption) {
            case "D4":
                activityClass = D4.class;
                diceSides = 4;
                break;
            case "D6":
                activityClass = D6.class;
                diceSides = 6;
                break;
            case "D8":
                activityClass = D8.class;
                diceSides = 8;
                break;
            case "D10":
                activityClass = D10.class;
                diceSides = 10;
                break;
            case "D12":
                activityClass = D12.class;
                diceSides = 12;
                break;
            case "D20":
                activityClass = D20.class;
                diceSides = 20;
                break;
            case "D100":
                activityClass = D100.class;
                diceSides = 100;
                break;
            case "D%":
                activityClass = DPO.class;
                diceSides = 100;
                break;
            default:
                throw new IllegalArgumentException("Invalid option selected");
        }
        Intent intent = new Intent(MainActivity.this, activityClass);
        intent.putExtra("selected_option", selectedOption);
        intent.putExtra("dice_sides", diceSides);
        startActivity(intent);
    }
}