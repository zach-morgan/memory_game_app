package com.example.zach.memorygame.LevelClasses;

import android.content.Intent;

import com.example.zach.memorygame.R;
import com.example.zach.memorygame.single_page_level;

public class S3L4 extends single_page_level {
    
    @Override
    protected void initializeGame() {
        flipIntervals = 10;
        cardFlipTimeUp = 2500;
        goals = new String[]{getString(R.string.s3l4_gold_time), getString(R.string.s3l4_gold_moves),
                getString(R.string.s3l4_silver_time), getString(R.string.s3l4_silver_moves),
                getString(R.string.s3l4_bronze_time), getString(R.string.s3l4_bronze_moves)};
        super.initializeSinglePageGame(true, 14, "Stage 3 Level 4", R.layout.s3l4);
    }
    
    @Override
    protected Intent getNextLevelIntent(){
        return null;
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S3L4_key);
    }
}
