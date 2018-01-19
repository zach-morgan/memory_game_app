package com.example.zach.memorygame;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Zach on 1/16/2018.
 */

public abstract class stage_select_base extends Fragment {

    protected View rootView;

    protected TextView amount_of_stars_until_next_stage;

    protected TextView curr_amount_of_stars;

    protected ViewPager pager;

    protected String[] keys;

    protected int[] stars;

    protected Typeface typefaceFont;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState
                            , String[] keys, ArrayList<Intent> intents) {
        super.onCreate(savedInstanceState);
        this.keys = keys;
        stars = new int[] {
                R.id.lvl1_bronze_place,R.id.lvl1_silver_place,R.id.lvl1_gold_place,R.id.lvl2_bronze_place,R.id.lvl2_silver_place,R.id.lvl2_gold_place,
                R.id.lvl3_bronze_place,R.id.lvl3_silver_place,R.id.lvl3_gold_place,R.id.lvl4_bronze_place,R.id.lvl4_silver_place,R.id.lvl4_gold_place};
        int theme = R.style.cartoon_levels_select;
        int font = R.font.finger_paint;
        typefaceFont = ResourcesCompat.getFont(getContext(),font);
        getActivity().setTheme(theme);
        rootView = inflater.inflate(R.layout.activity_level_select,container,false);
        configureBottomInfoBar();
        ArrayList<View> levelSelectors = new ArrayList<>(Arrays.asList(rootView.findViewById(R.id.stage1lvl1), rootView.findViewById(R.id.stage1lvl2),
                rootView.findViewById(R.id.stage1lvl3), rootView.findViewById(R.id.stage1lvl4)));
        setTitleBar(typefaceFont);
        setStarsandScore(keys,stars,rootView);
        registerLevelSelectors(levelSelectors,intents);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        setStarsandScore(keys,stars,rootView);
    }

    private void setFont(TextView view, Typeface font){
        view.setTypeface(font);
    }


    private void configureBottomInfoBar(){
        amount_of_stars_until_next_stage = (TextView)rootView.findViewById(R.id.level_select_stars_until_next_stage);
        curr_amount_of_stars = (TextView)rootView.findViewById(R.id.level_select_current_amount_of_stars);
        final View first_cluster = rootView.findViewById(R.id.level_select_bottom_startCluster);
        final TextView of12 = (TextView)rootView.findViewById(R.id.level_select_total_amount_of_stars_textView);
        setFont(of12,typefaceFont);
        setFont(amount_of_stars_until_next_stage,typefaceFont);
        setFont(curr_amount_of_stars,typefaceFont);

        final AlphaAnimation animation = new AlphaAnimation(0.0f,1.0f);
        animation.setDuration(500);
        animation.setStartOffset(4500);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        final AlphaAnimation animation2 = new AlphaAnimation(1.0f,0.0f);
        animation2.setDuration(500);
        animation2.setStartOffset(4500);
        animation2.setRepeatCount(Animation.INFINITE);
        animation2.setRepeatMode(Animation.REVERSE);

        curr_amount_of_stars.startAnimation(animation);
        first_cluster.startAnimation(animation);
        of12.startAnimation(animation);

        amount_of_stars_until_next_stage.startAnimation(animation2);
    }

    protected void setStarsandScore(String[] keys, int[] stars, View rootView){
        int score = 0;
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        for (int i = 0; i<keys.length;i++){
            ImageView bronze_star = rootView.findViewById(stars[i]);
            ImageView silver_star = rootView.findViewById(stars[i+1]);
            ImageView gold_star = rootView.findViewById(stars[i+2]);
            if (sharedPrefs.contains(keys[i])){
                String defaultValue = getString(R.string.shared_pref_no_medal);
                if (sharedPrefs.getString(keys[i],defaultValue).equals(getString(R.string.shared_pref_gold))) {
                    Glide.with(this).load(ContextCompat.getDrawable(getActivity(),R.drawable.level_select_gold_star)).into(gold_star);
                    Glide.with(this).load(ContextCompat.getDrawable(getActivity(),R.drawable.level_select_silver_star)).into(silver_star);
                    Glide.with(this).load(ContextCompat.getDrawable(getActivity(),R.drawable.level_select_bronze_star)).into(bronze_star);
                    score += 3;
                }
                if (sharedPrefs.getString(keys[i],defaultValue).equals(getString(R.string.shared_pref_silver))) {
                    Glide.with(this).load(ContextCompat.getDrawable(getActivity(),R.drawable.level_select_no_star)).into(gold_star);
                    Glide.with(this).load(ContextCompat.getDrawable(getActivity(),R.drawable.level_select_silver_star)).into(silver_star);
                    Glide.with(this).load(ContextCompat.getDrawable(getActivity(),R.drawable.level_select_bronze_star)).into(bronze_star);
                    score += 2;
                }
                if (sharedPrefs.getString(keys[i],defaultValue).equals(getString(R.string.shared_pref_bronze))) {
                    Glide.with(this).load(ContextCompat.getDrawable(getActivity(),R.drawable.level_select_no_star)).into(gold_star);
                    Glide.with(this).load(ContextCompat.getDrawable(getActivity(),R.drawable.level_select_no_star)).into(silver_star);
                    Glide.with(this).load(ContextCompat.getDrawable(getActivity(),R.drawable.level_select_bronze_star)).into(bronze_star);
                    score += 1;
                }
                if (sharedPrefs.getString(keys[i],defaultValue).equals(getString(R.string.shared_pref_no_medal))){
                    Glide.with(this).load(ContextCompat.getDrawable(getActivity(),R.drawable.level_select_no_star)).into(gold_star);
                    Glide.with(this).load(ContextCompat.getDrawable(getActivity(),R.drawable.level_select_no_star)).into(silver_star);
                    Glide.with(this).load(ContextCompat.getDrawable(getActivity(),R.drawable.level_select_no_star)).into(bronze_star);
                }
            }
            else if (!keys[i].equals("")){
                Glide.with(this).load(ContextCompat.getDrawable(getActivity(),R.drawable.level_select_no_star)).into(gold_star);
                Glide.with(this).load(ContextCompat.getDrawable(getActivity(),R.drawable.level_select_no_star)).into(silver_star);
                Glide.with(this).load(ContextCompat.getDrawable(getActivity(),R.drawable.level_select_no_star)).into(bronze_star);
            }
        }
        curr_amount_of_stars.setText(Integer.toString(score));
        int stars_needed = 10 - score;
        if (stars_needed >0) {
            amount_of_stars_until_next_stage.setText(getNextStageNum() + " In " + Integer.toString(stars_needed));
        }else{
            amount_of_stars_until_next_stage.setText(getNextStageNum() + " Unlocked!");

        }
    }

    protected void setTitleBar(Typeface font){
        TextView header = (TextView) rootView.findViewById(R.id.stage_header);
        header.setText(getStageNum());
        header.setTypeface(font);
    }

    protected void registerLevelSelectors(ArrayList<View> levelSelectors,ArrayList<Intent> intents) {
        Typeface font = ResourcesCompat.getFont(getContext(),R.font.finger_paint);
        for (int i = 0;i < intents.size();i++) {
            Button btn = (Button)levelSelectors.get(i);
            registerClickListeners(intents.get(i), btn);
            btn.setTypeface(font);
        }
    }

    private void registerClickListeners(final Intent intnt, View button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intnt);
            }
        });
    }

    protected abstract String getStageNum();

    protected abstract String getNextStageNum();
}
