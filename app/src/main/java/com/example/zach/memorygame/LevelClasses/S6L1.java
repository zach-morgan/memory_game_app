package com.example.zach.memorygame.LevelClasses;

import android.content.Intent;

import com.example.zach.memorygame.R;
import com.example.zach.memorygame.multi_page_level;

/**
 * Created by Zach on 2/3/2018.
 */

public class S6L1 extends multi_page_level {

    @Override
    protected void initializeGame() {
        goals = new String[]{getString(R.string.s6l1_gold_time), getString(R.string.s6l1_gold_moves),
                getString(R.string.s6l1_silver_time), getString(R.string.s6l1_silver_moves),
                getString(R.string.s6l1_bronze_time), getString(R.string.s6l1_bronze_moves)};
        cardFlipTimeUp = 1000;
        flipIntervals = 10;
        isMultiPage = true;
        int[] pageLayouts = new int[]{R.layout.s6l1_p1,R.layout.s6l1_p2,R.layout.s6l1_p3};
        super.initializeMultiPageGame(false, pageLayouts, 12,"Stage 6 Level 1");
    }

    @Override
    protected Intent getNextLevelIntent(){
        return new Intent(this, S6L2.class);
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S6L1_key);
    }
}
