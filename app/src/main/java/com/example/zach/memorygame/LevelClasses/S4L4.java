package com.example.zach.memorygame.LevelClasses;

import android.content.Intent;

import com.example.zach.memorygame.R;
import com.example.zach.memorygame.multi_page_level;

/**
 * Created by Zach on 1/31/2018.
 */

public class S4L4 extends multi_page_level {

    @Override
    protected void initializeGame() {
        goals = new String[]{getString(R.string.s4l4_gold_time), getString(R.string.s4l4_gold_moves),
                getString(R.string.s4l4_silver_time), getString(R.string.s4l4_silver_moves),
                getString(R.string.s4l4_bronze_time), getString(R.string.s4l4_bronze_moves)};
        cardFlipTimeUp = 1000;
        flipIntervals = 7;
        isMultiPage = true;
        int[] pageLayouts = new int[]{R.layout.s4l4_p1,R.layout.s4l4_p2};
        super.initializeMultiPageGame(true, pageLayouts, 20,"Stage 4 Level 4");
    }

    @Override
    protected Intent getNextLevelIntent(){
        return null;
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S4L4_key);
    }

}
