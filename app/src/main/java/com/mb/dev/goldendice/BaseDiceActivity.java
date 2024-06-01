package com.mb.dev.goldendice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public abstract class BaseDiceActivity extends AppCompatActivity {

    protected TextView resultText;
    protected Button rollButton;
    protected ImageButton imageButton;
    protected int diceSides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        resultText = findViewById(R.id.resultText);
        rollButton = findViewById(R.id.rollButton);
        imageButton = findViewById(R.id.imageButton);

        diceSides = getIntent().getIntExtra("dice_sides", 6);
        if (diceSides <= 0) {
            diceSides = 6;
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();
            }
        });
    }

    protected void rollDice() {
        Random random = new Random();
        int randomNumber = random.nextInt(diceSides) + 1;
        resultText.setText(String.valueOf(randomNumber));

        resultText.setVisibility(View.VISIBLE);
    }

    protected abstract int getLayoutId();
}