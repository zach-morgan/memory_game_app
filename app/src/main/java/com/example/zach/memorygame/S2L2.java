package com.example.zach.memorygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class S2L2 extends levels {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initializeGame() {
        model = new ConcentrationModel(10);
        model.addObserver(this);
        levelLabel.setText("Stage 2 Level 2");
        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        game_layout = getLayoutInflater().inflate(R.layout.activity_s2_l2,mainLayout,false);
        goals = new String[]{getString(R.string.s2l2_gold_time), getString(R.string.s2l2_gold_moves),
                getString(R.string.s2l2_silver_time), getString(R.string.s2l2_silver_moves),
                getString(R.string.s2l2_bronze_time), getString(R.string.s2l2_bronze_moves)};
        initializeLevelIntroduction(goals);
    }

    @Override
    protected void playGame() {
        loadImages(5,"Cartoon");
        buttons = new int[]{
                R.id.S2L2_B1,R.id.S2L2_B2,R.id.S2L2_B3,R.id.S2L2_B4,R.id.S2L2_B5,R.id.S2L2_B6,
                R.id.S2L2_B7,R.id.S2L2_B8,R.id.S2L2_B9,R.id.S2L2_B10
        };
        registersGameCards(buttons);
    }
    @Override
    protected Intent getNextLevelIntent(){
        return new Intent(this,S2L3.class);
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S2L2_key);
    }
}
