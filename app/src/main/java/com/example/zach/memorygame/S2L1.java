package com.example.zach.memorygame;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class S2L1 extends levels {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initializeGame() {
        model = new ConcentrationModel(6);
        model.addObserver(this);
        levelLabel.setText("Stage 2 Level 1");
        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        hasFlip = true;
        flipIntervals = 7;
        cardFlipTimeUp = 2000;
        game_layout = getLayoutInflater().inflate(R.layout.activity_s2_l1,mainLayout,false);
        goals = new String[]{getString(R.string.s2l1_gold_time), getString(R.string.s2l1_gold_moves),
                getString(R.string.s2l1_silver_time), getString(R.string.s2l1_silver_moves),
                getString(R.string.s2l1_bronze_time), getString(R.string.s2l1_bronze_moves)};
        initializeLevelIntroduction(goals);
    }

    @Override
    protected void playGame() {
        loadImages(3,"Cartoon");
        buttons = new int[]{
                R.id.S2L1_B1,R.id.S2L1_B2,R.id.S2L1_B3,R.id.S2L1_B4,R.id.S2L1_B5,R.id.S2L1_B6
        };
        registersGameCards(buttons);
    }
    @Override
    protected Intent getNextLevelIntent(){
        return new Intent(this,S2L2.class);
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S2L1_key);
    }
}
