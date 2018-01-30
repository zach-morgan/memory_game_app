package com.example.zach.memorygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Zach on 1/29/2018.
 */

public abstract class multi_page_level extends levels {

    ViewPager pager;

    private ArrayList<level_fragment> fragments;

    int pageNum;

    int currentPage;

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initializeMultiPageGame(boolean hasSwap, int[] pageLayouts, int numCards, String headerText) {
        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        game_layout = getLayoutInflater().inflate(R.layout.multi_page_level_pager, mainLayout,false);
        pager = game_layout.findViewById(R.id.level_pager);
        pager.setOffscreenPageLimit(2);
        pager.setPageTransformer(true, new CubeOutTransformer());
        fragments = new ArrayList<>();
        for (int pageID : pageLayouts){
            Bundle arg1 = new Bundle();
            arg1.putInt(getString(R.string.multi_page_level_key),pageID);
            arg1.putBoolean(getString(R.string.multi_page_has_flip_key),hasSwap);
            level_fragment Page = new level_fragment();
            Page.setArguments(arg1);
            fragments.add(Page);
        }
        pageNum = fragments.size();
        currentPage = 0;
        multi_page_level_pager pag = new multi_page_level_pager(getSupportFragmentManager(), fragments);
        pager.setAdapter(pag);
        model = new ConcentrationModel(numCards);
        model.addObserver(this);
        loadImages(numCards / 2);
        levelLabel.setText(headerText);
        //GOALS MUST BE SET BEFORE FUNCTION CALL
        initializeLevelIntroduction(goals);
    }

    protected void displayMultiplePages(){
        pager.setCurrentItem(0);
        updateBoard(model.getCards());
        timer = new Timer();
        timer.scheduleAtFixedRate(new switcherTimer(),1500, 1000);
    }

    protected void updateDaBoard(){
        updateBoard(model.getCards());
    }


    class switcherTimer extends TimerTask {
        boolean goBack = false;
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (currentPage >= pageNum-1) {
                        goBack = true;
                    }
                    if (goBack) {
                        pager.setCurrentItem(--currentPage);
                        if (currentPage == 0){
                            timer.cancel();
                        }
                    }else{
                        pager.setCurrentItem(++currentPage);
                    }
                }
            });
        }
    }

}
