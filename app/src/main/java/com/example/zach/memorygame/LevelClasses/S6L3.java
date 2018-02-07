package com.example.zach.memorygame.LevelClasses;

import android.content.Intent;

import com.example.zach.memorygame.R;
import com.example.zach.memorygame.multi_page_level;

/**
 * Created by Zach on 2/3/2018.
 */

public class S6L3 extends multi_page_level {

    @Override
    protected void initializeGame() {
        goals = new String[]{getString(R.string.s6l3_gold_time), getString(R.string.s6l3_gold_moves),
                getString(R.string.s6l3_silver_time), getString(R.string.s6l3_silver_moves),
                getString(R.string.s6l3_bronze_time), getString(R.string.s6l3_bronze_moves)};
        cardFlipTimeUp = 1000;
        flipIntervals = 10;
        isMultiPage = true;
        int[] pageLayouts = new int[]{R.layout.s6l3_p1,R.layout.s6l3_p2,R.layout.s6l3_p3};
        super.initializeMultiPageGame(false, pageLayouts, 20,"Stage 6 Level 3");
    }

    @Override
    protected Intent getNextLevelIntent(){
        return new Intent(this, S6L4.class);
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S6L3_key);
    }
}
