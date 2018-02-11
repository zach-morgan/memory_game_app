package com.example.zach.memorygame.LevelClasses;

import android.content.Intent;

import com.example.zach.memorygame.R;
import com.example.zach.memorygame.single_page_level;

/**
 * Created by Zach on 1/9/2018.
 */

public class S1L1 extends single_page_level {

    @Override
    protected void initializeGame() {
        goals = new String[]{getString(R.string.s1l1_gold_time), getString(R.string.s1l1_gold_moves),
                getString(R.string.s1l1_silver_time), getString(R.string.s1l1_silver_moves),
                getString(R.string.s1l1_bronze_time), getString(R.string.s1l1_bronze_moves)};
        super.initializeSinglePageGame(false, 6, "Stage 1 level 1", R.layout.s1l1);
    }



    @Override
    protected Intent getNextLevelIntent(){
        return new Intent(this,S1L2.class);
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S1L1_key);
    }
}
