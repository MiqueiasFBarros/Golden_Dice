package com.mb.dev.goldendice;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DPO extends BaseDiceActivity {

    private TextView resultText;
    private Button rollButton;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView diceImage = findViewById(R.id.diceImage);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dpo;
    }

    @Override
    protected void rollDice() {
        super.rollDice();
    }
}