package com.example.zach.memorygame.LevelClasses;

import android.content.Intent;

import com.example.zach.memorygame.R;
import com.example.zach.memorygame.multi_page_level;

/**
 * Created by Zach on 1/31/2018.
 */

public class S4L2 extends multi_page_level {

    @Override
    protected void initializeGame() {
        goals = new String[]{getString(R.string.s4l2_gold_time), getString(R.string.s4l2_gold_moves),
                getString(R.string.s4l2_silver_time), getString(R.string.s4l2_silver_moves),
                getString(R.string.s4l2_bronze_time), getString(R.string.s4l2_bronze_moves)};
        cardFlipTimeUp = 1000;
        flipIntervals = 7;
        isMultiPage = true;
        int[] pageLayouts = new int[]{R.layout.s4l2_p1,R.layout.s4l2_p2};
        super.initializeMultiPageGame(true, pageLayouts, 12,"Stage 4 Level 2");
    }

    @Override
    protected Intent getNextLevelIntent(){
        return new Intent(this,S4L3.class);
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S4L2_key);
    }
}
