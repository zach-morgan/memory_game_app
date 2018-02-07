package com.example.zach.memorygame.LevelClasses;

import android.content.Intent;

import com.example.zach.memorygame.R;
import com.example.zach.memorygame.single_page_level;

public class S3L1 extends single_page_level {

    @Override
    protected void initializeGame() {
        flipIntervals = 7;
        cardFlipTimeUp = 2000;
        goals = new String[]{getString(R.string.s3l1_gold_time), getString(R.string.s3l1_gold_moves),
                getString(R.string.s3l1_silver_time), getString(R.string.s3l1_silver_moves),
                getString(R.string.s3l1_bronze_time), getString(R.string.s3l1_bronze_moves)};
        super.initializeSinglePageGame(true, 6, "Stage 3 Level 1", R.layout.s3l1);
    }

    @Override
    protected Intent getNextLevelIntent(){
        return new Intent(this,S3L2.class);
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S3L1_key);
    }
}
