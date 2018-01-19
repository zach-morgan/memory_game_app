package com.example.zach.memorygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class S2L3 extends levels {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initializeGame() {
        model = new ConcentrationModel(12);
        model.addObserver(this);
        levelLabel.setText("Stage 2 Level 3");
        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        game_layout = getLayoutInflater().inflate(R.layout.activity_s2_l3,mainLayout,false);
        goals = new String[]{getString(R.string.s2l3_gold_time), getString(R.string.s2l3_gold_moves),
                getString(R.string.s2l3_silver_time), getString(R.string.s2l3_silver_moves),
                getString(R.string.s2l3_bronze_time), getString(R.string.s2l3_bronze_moves)};
        initializeLevelIntroduction(goals);
    }

    @Override
    protected void playGame() {
        loadImages(6,"Cartoon");
        buttons = new int[]{
                R.id.S2L3_B1,R.id.S2L3_B2,R.id.S2L3_B3,R.id.S2L3_B4,R.id.S2L3_B5,R.id.S2L3_B6,
                R.id.S2L3_B7,R.id.S2L3_B8,R.id.S2L3_B9,R.id.S2L3_B10,R.id.S2L3_B11,R.id.S2L3_B12
        };
        registersGameCards(buttons);
    }
    @Override
    protected Intent getNextLevelIntent(){
        return new Intent(this,S2L4.class);
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S2L3_key);
    }
}
