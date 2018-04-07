package com.example.zach.memorygame;

import android.animation.Animator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Zach on 1/11/2018.
 */

public class animatorListener implements Animator.AnimatorListener {

    private ArrayList<View> views;

    private ViewGroup parent;

    public animatorListener(ViewGroup parent, ArrayList<View> views){
        this.views = views;
        this.parent = parent;

    }

    @Override
    public void onAnimationCancel(Animator animator) {
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        for (View view : views){
            parent.removeView(view);
        }

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

    @Override
    public void onAnimationStart(Animator animator) {

    }
}
