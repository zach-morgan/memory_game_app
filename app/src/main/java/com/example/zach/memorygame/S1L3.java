package com.example.zach.memorygame;

import android.content.Intent;
import android.widget.LinearLayout;

/**
 * Created by Zach on 1/9/2018.
 */

public class S1L3 extends levels {
    @Override
    protected void initializeGame() {
        model = new ConcentrationModel(10);
        model.addObserver(this);
        levelLabel.setText("Stage 1 Level 3");
        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        game_layout = getLayoutInflater().inflate(R.layout.s1l3,mainLayout,false);
        goals = new String[]{getString(R.string.s1l3_gold_time), getString(R.string.s1l3_gold_moves),
                getString(R.string.s1l3_silver_time), getString(R.string.s1l3_silver_moves),
                getString(R.string.s1l3_bronze_time), getString(R.string.s1l3_bronze_moves)};
        initializeLevelIntroduction(goals);
    }

    @Override
    protected void playGame() {
        loadImages(5,"Cartoon");
        buttons = new int[]{
                R.id.S1L3_B1,R.id.S1L3_B2,R.id.S1L3_B3,R.id.S1L3_B4,R.id.S1L3_B5,R.id.S1L3_B6,R.id.S1L3_B7,R.id.S1L3_B8,R.id.S1L3_B9,R.id.S1L3_B10
        };
        registersGameCards(buttons);
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S1L3_key);
    }

    @Override
    protected Intent getNextLevelIntent() {
        return new Intent(this,S1L4.class);
    }}
