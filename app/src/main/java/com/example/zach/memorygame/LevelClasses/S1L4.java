package com.example.zach.memorygame.LevelClasses;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.widget.LinearLayout;

import com.example.zach.memorygame.R;
import com.example.zach.memorygame.single_page_level;

/**
 * Created by Zach on 1/9/2018.
 */

public class S1L4 extends single_page_level {

    @Override
    protected void initializeGame() {
        goals = new String[]{getString(R.string.s1l4_gold_time), getString(R.string.s1l4_gold_moves),
                getString(R.string.s1l4_silver_time), getString(R.string.s1l4_silver_moves),
                getString(R.string.s1l4_bronze_time), getString(R.string.s1l4_bronze_moves)};
        super.initializeSinglePageGame(false, 12, "Stage 1 Level 4", R.layout.s1l4);
    }



    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S1L4_key);
    }

    @Override
    protected Intent getNextLevelIntent() {
        return null;
    }
}
