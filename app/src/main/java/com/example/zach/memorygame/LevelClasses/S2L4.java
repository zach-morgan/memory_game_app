package com.example.zach.memorygame.LevelClasses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zach.memorygame.R;
import com.example.zach.memorygame.multi_page_level;

public class S2L4 extends multi_page_level {
    @Override
    protected void initializeGame() {
        goals = new String[]{getString(R.string.s2l4_gold_time), getString(R.string.s2l4_gold_moves),
                getString(R.string.s2l4_silver_time), getString(R.string.s2l4_silver_moves),
                getString(R.string.s2l4_bronze_time), getString(R.string.s2l4_bronze_moves)};
        int[] pageLayouts = new int[]{R.layout.s2l4_p1,R.layout.s2l4_p2};
        super.initializeMultiPageGame(false, pageLayouts, 16,"Stage 2 Level 4");
    }

    @Override
    protected Intent getNextLevelIntent(){
        return null;
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S2L4_key);
    }

}
