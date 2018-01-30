package com.example.zach.memorygame;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Zach on 1/28/2018.
 */

public class multi_page_level_pager extends FragmentStatePagerAdapter {

    ArrayList<level_fragment> fragments = new ArrayList<>();

    public multi_page_level_pager(FragmentManager fm, ArrayList<level_fragment> fragments){
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
