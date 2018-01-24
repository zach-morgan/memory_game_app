package com.example.zach.memorygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.widget.LinearLayout;

/**
 * Created by Zach on 1/9/2018.
 */

public class S1L1 extends levels {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initializeGame() {
        model = new ConcentrationModel(6);
        model.addObserver(this);
        levelLabel.setText("Stage 1 Level 1");
        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        game_layout = getLayoutInflater().inflate(R.layout.s1l1,mainLayout,false);
        goals = new String[]{getString(R.string.s1l1_gold_time), getString(R.string.s1l1_gold_moves),
                getString(R.string.s1l1_silver_time), getString(R.string.s1l1_silver_moves),
                getString(R.string.s1l1_bronze_time), getString(R.string.s1l1_bronze_moves)};
        initializeLevelIntroduction(goals);
    }

    @Override
    protected void playGame() {
        loadImages(3);
        buttons = new int[]{
                R.id.S1L1_B1,R.id.S1L1_B2,R.id.S1L1_B3,R.id.S1L1_B4,R.id.S1L1_B5,R.id.S1L1_B6
        };
        registersGameCards(buttons);
    }
    @Override
    protected Intent getNextLevelIntent(){
        return new Intent(this,S1L2.class);
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S1L1_key);
    }
}
