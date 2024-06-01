package com.mb.dev.goldendice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView diceImage;
    private TextView resultText;
    private Button rollButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        diceImage = findViewById(R.id.diceImage);
        resultText = findViewById(R.id.resultText);
        rollButton = findViewById(R.id.rollButton);

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();
            }
        });
    }

    private void rollDice() {
        Random random = new Random();
        int randomNumber = random.nextInt(20) + 1;
        resultText.setText(String.valueOf(randomNumber));

        int diceWidth = diceImage.getWidth();

        int textWidth = resultText.getWidth();

        resultText.setVisibility(View.VISIBLE);
    }
}
