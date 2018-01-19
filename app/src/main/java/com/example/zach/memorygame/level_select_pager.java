package com.example.zach.memorygame;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Zach on 1/16/2018.
 */

public class level_select_pager extends FragmentStatePagerAdapter {

    public level_select_pager(FragmentManager fm){
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch(position){
            case 0:
                return new stage1_select();
            case 1:
                return new stage2_select();
            case 2:
                return new stage3_select();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
