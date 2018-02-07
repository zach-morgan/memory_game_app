package com.example.zach.memorygame.LevelClasses;

import android.content.Intent;

import com.example.zach.memorygame.R;
import com.example.zach.memorygame.multi_page_level;

/**
 * Created by Zach on 2/3/2018.
 */

public class S5L3 extends multi_page_level {
    @Override
    protected void initializeGame() {
        goals = new String[]{getString(R.string.s5l3_gold_time), getString(R.string.s5l3_gold_moves),
                getString(R.string.s5l3_silver_time), getString(R.string.s5l3_silver_moves),
                getString(R.string.s5l3_bronze_time), getString(R.string.s5l3_bronze_moves)};
        cardFlipTimeUp = 1000;
        flipIntervals = 9;
        isMultiPage = true;
        int[] pageLayouts = new int[]{R.layout.s5l3_p1,R.layout.s5l3_p2};
        super.initializeMultiPageGame(true, pageLayouts, 22,"Stage 5 Level 3");
    }

    @Override
    protected Intent getNextLevelIntent(){
        return new Intent(this,S5L4.class);
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S5L3_key);
    }
}
