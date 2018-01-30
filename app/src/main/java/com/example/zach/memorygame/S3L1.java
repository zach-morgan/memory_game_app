package com.example.zach.memorygame;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.ToxicBakery.viewpager.transforms.StackTransformer;

import java.util.ArrayList;
import java.util.Arrays;

public class S3L1 extends multi_page_level{

    ViewPager pager;

    private ArrayList<level_fragment> fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initializeGame() {
        goals = new String[]{getString(R.string.s2l2_gold_time), getString(R.string.s2l2_gold_moves),
                getString(R.string.s2l2_silver_time), getString(R.string.s2l2_silver_moves),
                getString(R.string.s2l2_bronze_time), getString(R.string.s2l2_bronze_moves)};
        int[] pageLayouts = new int[]{R.layout.activity_s3_l1_p1,R.layout.activity_s3_l1_p2};
        super.initializeMultiPageGame(false, pageLayouts, 6,"Stage 3 Level 1");
    }

    @Override
    protected void playGame() {
        super.displayMultiplePages();
    }

    @Override
    protected Intent getNextLevelIntent(){
        return new Intent(this,S3L2.class);
    }

    @Override
    protected String getKey() {
        return getString(R.string.shared_pref_S3L1_key);
    }



}
