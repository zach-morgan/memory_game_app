package com.example.zach.memorygame.LevelClasses;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.zach.memorygame.R;
import com.example.zach.memorygame.multi_page_level;

public class S4L1 extends multi_page_level {

    @Override
    protected void initializeGame() {
        goals = new String[]{getString(R.string.s4l1_gold_time), getString(R.string.s4l1_gold_moves),
                getString(R.string.s4l1_silver_time), getString(R.string.s4l1_silver_moves),
                getString(R.string.s4l1_bronze_time), getString(R.string.s4l1_bronze_moves)};
        cardFlipTimeUp = 500;
        flipIntervals = 8;
        isMultiPage = true;
        int[] pageLayouts = new int[]{R.layout.s4l1_p1,R.layout.s4l1_p2};
        super.initializeMultiPageGame(true, pageLayouts, 8,"Stage 4 Level 1");
    }

    @Override
    protected Intent getNextLevelIntent(){
        return new Intent(this,S4L2.class);
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S4L1_key);
    }
}
