package com.example.zach.memorygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class S2L4 extends levels {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initializeGame() {
        model = new ConcentrationModel(14);
        model.addObserver(this);
        levelLabel.setText("Stage 2 Level 4");
        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        game_layout = getLayoutInflater().inflate(R.layout.activity_s2_l4,mainLayout,false);
        goals = new String[]{getString(R.string.s2l4_gold_time), getString(R.string.s2l4_gold_moves),
                getString(R.string.s2l4_silver_time), getString(R.string.s2l4_silver_moves),
                getString(R.string.s2l4_bronze_time), getString(R.string.s2l4_bronze_moves)};
        initializeLevelIntroduction(goals);
    }

    @Override
    protected void playGame() {
        loadImages(7,"Cartoon");
        buttons = new int[]{
                R.id.S2L4_B1,R.id.S2L4_B2,R.id.S2L4_B3,R.id.S2L4_B4,R.id.S2L4_B5,R.id.S2L4_B6,R.id.S2L4_B7,
                R.id.S2L4_B8,R.id.S2L4_B9,R.id.S2L4_B10,R.id.S2L4_B11,R.id.S2L4_B12,R.id.S2L4_B13,R.id.S2L4_B14
        };
        registersGameCards(buttons);
    }
    @Override
    protected Intent getNextLevelIntent(){
        return new Intent(this,S2L4.class);
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S2L4_key);
    }
}
