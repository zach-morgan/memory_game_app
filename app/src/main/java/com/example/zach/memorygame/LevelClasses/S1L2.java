package com.example.zach.memorygame.LevelClasses;

import android.content.Intent;

import com.example.zach.memorygame.R;
import com.example.zach.memorygame.single_page_level;

/**
 * Created by Zach on 1/9/2018.
 */

public class S1L2 extends single_page_level {

    @Override
    protected void initializeGame() {
        goals = new String[]{getString(R.string.s1l2_gold_time), getString(R.string.s1l2_gold_moves),
                getString(R.string.s1l2_silver_time), getString(R.string.s1l2_silver_moves),
                getString(R.string.s1l2_bronze_time), getString(R.string.s1l2_bronze_moves)};
        super.initializeSinglePageGame(false,8,"Stage 1 Level 2", R.layout.s1l2);
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
