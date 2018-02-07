package com.example.zach.memorygame.LevelClasses;

import android.content.Intent;

import com.example.zach.memorygame.R;
import com.example.zach.memorygame.single_page_level;

/**
 * Created by Zach on 1/9/2018.
 */

public class S1L3 extends single_page_level {
    @Override
    protected void initializeGame() {
        goals = new String[]{getString(R.string.s1l3_gold_time), getString(R.string.s1l3_gold_moves),
                getString(R.string.s1l3_silver_time), getString(R.string.s1l3_silver_moves),
                getString(R.string.s1l3_bronze_time), getString(R.string.s1l3_bronze_moves)};
        super.initializeSinglePageGame(false, 10, "Stage 1 Level 3", R.layout.s1l3);
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S1L3_key);
    }

    @Override
    protected Intent getNextLevelIntent() {
        return new Intent(this,S1L4.class);
    }}
