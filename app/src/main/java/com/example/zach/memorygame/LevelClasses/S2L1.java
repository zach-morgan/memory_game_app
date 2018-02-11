package com.example.zach.memorygame.LevelClasses;

import android.content.Intent;
import android.os.Bundle;

import com.example.zach.memorygame.R;
import com.example.zach.memorygame.multi_page_level;

public class S2L1 extends multi_page_level {
    @Override
    protected void initializeGame() {
        hasSwipeHelp = true;
        goals = new String[]{getString(R.string.s2l1_gold_time),getString(R.string.s2l1_gold_moves),
                getString(R.string.s2l1_silver_time), getString(R.string.s2l1_silver_moves),
                getString(R.string.s2l1_bronze_time), getString(R.string.s2l1_bronze_moves)};
        int[] pageLayouts = new int[]{R.layout.s2l1_p1,R.layout.s2l1_p2};
        super.initializeMultiPageGame(false, pageLayouts, 6,"Stage 2 Level 1");
    }

    @Override
    protected Intent getNextLevelIntent(){
        return new Intent(this,S2L2.class);
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S2L1_key);
    }



}
