package com.example.zach.memorygame;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Zach on 1/28/2018.
 */

public class level_fragment extends Fragment {

    View mainView;

    boolean hasFlip;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //int layout = getArguments().getInt(getString(R.string.multi_page_level_key),R.layout.level_introduction);
        //hasFlip = getArguments().getBoolean(getString(R.string.multi_page_has_flip_key),false);
        //mainView = inflater.inflate(layout,container,false);;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout = getArguments().getInt(getString(R.string.multi_page_level_key),R.layout.activity_home_page);
        hasFlip = getArguments().getBoolean(getString(R.string.multi_page_has_flip_key),false);
        mainView = inflater.inflate(layout,null);
        return mainView;
    }

    @Override
    public void onStart() {
        super.onStart();
        multi_page_level parentActivity = (multi_page_level)getActivity();
        parentActivity.registersGameCards(getButtons());
        parentActivity.updateDaBoard();
    }

    public int[] getButtons(){
        int endingIndex = 0;
        ViewGroup mainGroup = (ViewGroup)mainView;
        if (hasFlip){
            endingIndex = mainGroup.getChildCount() -1;
        }else{
            endingIndex = mainGroup.getChildCount();
        }
        int[] buttonIDs = new int[endingIndex];
        for (int index = 0; index < endingIndex; index++){
            buttonIDs[index] = mainGroup.getChildAt(index).getId();
        }
        return buttonIDs;
    }
}
