package com.example.zach.memorygame;

import android.content.Intent;
import android.widget.LinearLayout;

/**
 * Created by Zach on 1/9/2018.
 */

public class S1L2 extends levels {

    @Override
    protected void initializeGame() {
        model = new ConcentrationModel(8);
        model.addObserver(this);
        levelLabel.setText("Stage 1 Level 2");
        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        game_layout = getLayoutInflater().inflate(R.layout.s1l2,mainLayout,false);
        goals = new String[]{getString(R.string.s1l2_gold_time), getString(R.string.s1l2_gold_moves),
                getString(R.string.s1l2_silver_time), getString(R.string.s1l2_silver_moves),
                getString(R.string.s1l2_bronze_time), getString(R.string.s1l2_bronze_moves)};
        initializeLevelIntroduction(goals);
    }

    @Override
    protected void playGame() {
        loadImages(4,"Cartoon");
        buttons = new int[]{
                R.id.S1L2_B1,R.id.S1L2_B2,R.id.S1L2_B3,R.id.S1L2_B4,R.id.S1L2_B5,R.id.S1L2_B6,R.id.S1L2_B7,R.id.S1L2_B8
        };
        registersGameCards(buttons);
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S1L2_key);
    }

    @Override
    protected Intent getNextLevelIntent() {
        return new Intent(this, S1L3.class);
    }
}
