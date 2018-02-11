package com.example.zach.memorygame.LevelClasses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zach.memorygame.R;
import com.example.zach.memorygame.multi_page_level;

public class S2L3 extends multi_page_level {

    @Override
    protected void initializeGame() {
        hasSwipeHelp = true;
        goals = new String[]{getString(R.string.s2l3_gold_time), getString(R.string.s2l3_gold_moves),
                getString(R.string.s2l3_silver_time), getString(R.string.s2l3_silver_moves),
                getString(R.string.s2l3_bronze_time), getString(R.string.s2l3_bronze_moves)};
        int[] pageLayouts = new int[]{R.layout.s2l3_p1,R.layout.s2l3_p2};
        super.initializeMultiPageGame(false, pageLayouts, 12,"Stage 2 Level 3");
    }

    @Override
    protected Intent getNextLevelIntent(){
        return new Intent(this,S2L4.class);
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S2L3_key);
    }
}
