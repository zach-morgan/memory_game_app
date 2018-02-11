package com.example.zach.memorygame.LevelClasses;

import android.content.Intent;

import com.example.zach.memorygame.R;
import com.example.zach.memorygame.single_page_level;

public class S3L3 extends single_page_level {

    @Override
    protected void initializeGame() {
        flipIntervals = 15;
        cardFlipTimeUp = 2350;
        goals = new String[]{getString(R.string.s3l3_gold_time), getString(R.string.s3l3_gold_moves),
                getString(R.string.s3l3_silver_time), getString(R.string.s3l3_silver_moves),
                getString(R.string.s3l3_bronze_time), getString(R.string.s3l3_bronze_moves)};
        super.initializeSinglePageGame(true, 12, "Stage 3 Level 3", R.layout.s3l3);
    }

    @Override
    protected Intent getNextLevelIntent(){
        return new Intent(this,S3L4.class);
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S3L3_key);
    }
}
