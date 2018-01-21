package com.example.zach.memorygame;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.widget.LinearLayout;

/**
 * Created by Zach on 1/9/2018.
 */

public class S1L4 extends levels {

    @Override
    protected void initializeGame() {
        model = new ConcentrationModel(12);
        model.addObserver(this);
        levelLabel.setText("Stage 1 Level 4");
        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        game_layout = getLayoutInflater().inflate(R.layout.s1l4,mainLayout,false);
        goals = new String[]{getString(R.string.s1l4_gold_time), getString(R.string.s1l4_gold_moves),
                getString(R.string.s1l4_silver_time), getString(R.string.s1l4_silver_moves),
                getString(R.string.s1l4_bronze_time), getString(R.string.s1l4_bronze_moves)};
        initializeLevelIntroduction(goals);
    }

    @Override
    protected void playGame() {
        loadImages(6,"Cartoon");
        buttons = new int[]{
                R.id.S1L4_B1,R.id.S1L4_B2,R.id.S1L4_B3,R.id.S1L4_B4,R.id.S1L4_B5,R.id.S1L4_B6,R.id.S1L4_B7,R.id.S1L4_B8,R.id.S1L4_B9,R.id.S1L4_B10
                ,R.id.S1L4_B11,R.id.S1L4_B12
        };
        registersGameCards(buttons);
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S1L4_key);
    }

    @Override
    protected Intent getNextLevelIntent() {
        return null;
    }
}
