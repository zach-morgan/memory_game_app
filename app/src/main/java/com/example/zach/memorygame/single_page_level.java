package com.example.zach.memorygame;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Zach on 1/30/2018.
 */

public abstract class single_page_level extends levels {

    protected int numCards;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    protected void initializeSinglePageGame(boolean hasSwap, int numCards, String headerText, int layout){
        model = new ConcentrationModel(numCards);
        model.addObserver(this);
        this.numCards = numCards;
        levelLabel.setText(headerText);
        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        hasFlip = hasSwap;
        game_layout = getLayoutInflater().inflate(layout, mainLayout,false);
        initializeLevelIntroduction(goals);
    }

    @Override
    protected void playGame() {
        loadImages(numCards / 2);
        int[] buttonarray = getButtons();
        registersGameCards(buttonarray,true);
        updateBoard(model.getCards());
    }

    private int[] getButtons(){
        int endingIndex = 0;
        ViewGroup mainGroup = (ViewGroup)game_layout;
        if (hasFlip){
            endingIndex = mainGroup.getChildCount() -1;
        }else{
            endingIndex = mainGroup.getChildCount();
        }
        int[] result = new int[endingIndex];
        for (int index = 0; index < endingIndex; index++){
            result[index] = mainGroup.getChildAt(index).getId();
        }
        return result;
    }
}