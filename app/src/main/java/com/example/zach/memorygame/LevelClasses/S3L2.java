package com.example.zach.memorygame.LevelClasses;

import android.content.Intent;

import com.example.zach.memorygame.R;
import com.example.zach.memorygame.single_page_level;

public class S3L2 extends single_page_level {

    @Override
    protected void initializeGame() {
        flipIntervals = 8;
        cardFlipTimeUp = 2250;
        goals = new String[]{getString(R.string.s3l2_gold_time), getString(R.string.s3l2_gold_moves),
                getString(R.string.s3l2_silver_time), getString(R.string.s3l2_silver_moves),
                getString(R.string.s3l2_bronze_time), getString(R.string.s3l2_bronze_moves)};
        super.initializeSinglePageGame(true, 10, "Stage 3 Level 2", R.layout.s3l2);
    }

    @Override
    protected Intent getNextLevelIntent(){
        return new Intent(this,S3L3.class);
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S3L2_key);
    }
}
