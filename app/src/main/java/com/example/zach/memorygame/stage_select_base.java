package com.example.zach.memorygame;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
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

public abstract class stage_select_base extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    protected View rootView;

    protected TextView amount_of_stars_until_next_stage;

    protected TextView curr_amount_of_stars;

    protected ViewPager pager;

    protected String[] keys;

    protected int[] stars;

    protected Typeface typefaceFont;

    int theme,lockedLevel, bodyBackground, bottomTextHolder, fontColor;

    ArrayList<Intent> intents;

    SharedPreferences sharedPrefs;

    ArrayList<Button> levelSelectors;

    View first_cluster;

    protected static Animation nextStageanimation;

    TextView of12;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState
                            , String[] keys, ArrayList<Intent> intents) {
        super.onCreate(savedInstanceState);
        this.keys = keys;
        this.intents = intents;
        stars = new int[] {
                R.id.lvl1_bronze_place,R.id.lvl1_silver_place,R.id.lvl1_gold_place,R.id.lvl2_bronze_place,R.id.lvl2_silver_place,R.id.lvl2_gold_place,
                R.id.lvl3_bronze_place,R.id.lvl3_silver_place,R.id.lvl3_gold_place,R.id.lvl4_bronze_place,R.id.lvl4_silver_place,R.id.lvl4_gold_place};
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        setTheme();
        rootView = inflater.inflate(R.layout.activity_level_select,container,false);
        amount_of_stars_until_next_stage = (TextView)rootView.findViewById(R.id.level_select_stars_until_next_stage);
        curr_amount_of_stars = (TextView)rootView.findViewById(R.id.level_select_current_amount_of_stars);
        first_cluster = rootView.findViewById(R.id.level_select_bottom_startCluster);
        of12 = (TextView)rootView.findViewById(R.id.level_select_total_amount_of_stars_textView);
        setTitleBar();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        View backgroundLayout = rootView.findViewById(R.id.level_select_constraint);
        backgroundLayout.setBackground(ContextCompat.getDrawable(getContext(),bodyBackground));
        sharedPrefs.registerOnSharedPreferenceChangeListener(this);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        levelSelectors = new ArrayList<>(Arrays.asList((Button)rootView.findViewById(R.id.stage1lvl1),(Button) rootView.findViewById(R.id.stage1lvl2),
                (Button)rootView.findViewById(R.id.stage1lvl3),(Button) rootView.findViewById(R.id.stage1lvl4)));
        int score_needed = setStarsandScore(keys, stars, rootView);
        ImageView lock = rootView.findViewById(R.id.stage_locked_lock);
        if (score_needed <= 0){
            if (!sharedPrefs.contains(getNextStageUnlockStatusKey())){
                //animate new stage\
                final TextView next_stage_popup = rootView.findViewById(R.id.level_select_next_stage_unlocked_prompt);
                setFont(next_stage_popup,typefaceFont);
                next_stage_popup.setVisibility(View.VISIBLE);
                nextStageanimation = new AlphaAnimation(0.0f,1.0f);
                nextStageanimation.setRepeatCount(Animation.INFINITE);
                nextStageanimation.setRepeatMode(Animation.REVERSE);
                nextStageanimation.setDuration(1000);
                nextStageanimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        next_stage_popup.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                next_stage_popup.setAnimation(nextStageanimation);
                editor.putBoolean(getNextStageUnlockStatusKey(),true);
                editor.apply();
            }
        }
        if (isUnlocked()){
            setBackgroundTransparent(false);
            configureBottomInfoBar();
            registerLevelSelectors(levelSelectors,intents,true);
            lock.setVisibility(View.INVISIBLE);
        }else{
            setBackgroundTransparent(true);
            registerLevelSelectors(levelSelectors,intents,false);
            Glide.with(this).load(ContextCompat.getDrawable(getContext(),lockedLevel)).into(lock);
        }
    }

    public void setTheme(){
        String themeString = sharedPrefs.getString(getString(R.string.shared_pref_theme_key),"cartoon");
        int font;
        switch(themeString){
            case "cartoon":
                font = R.font.finger_paint;
                typefaceFont = ResourcesCompat.getFont(getContext(),font);
                theme = R.style.cartoon_levels_select;
                lockedLevel = R.drawable.stage_locked_lock;
                bodyBackground = R.drawable.cartoon_level_background;
                bottomTextHolder = R.drawable.cartoon_text_holder;
                fontColor = R.color.cartoon_Text;
                break;
            case "murica":
                font = R.font.black_ops_one;
                typefaceFont = ResourcesCompat.getFont(getContext(),font);
                theme = R.style.murica_levels_select;
                lockedLevel = R.drawable.murica_lock;
                bodyBackground = R.drawable.murica_level_select_background;
                bottomTextHolder = R.drawable.murica_text_holder;
                fontColor = R.color.murica_flag_blue;
        }
        getActivity().setTheme(theme);
    }

    @Override
    public void onPause() {
        super.onPause();
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(this);
        if (nextStageanimation != null){
            nextStageanimation.cancel();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getUnlockStatusKey())){
            onResume();
        }
    }

    private void setFont(TextView view, Typeface font){
        view.setTypeface(font);
    }


    private void setBackgroundTransparent(boolean setTrans){
        for (Button btn : levelSelectors){
            if (setTrans) {
                btn.setAlpha(0.5f);
            }else{
                btn.setAlpha(1f);
            }
        }
        for (int star : stars){
            if(setTrans) {
                rootView.findViewById(star).setAlpha(0.5f);
            }else{
                rootView.findViewById(star).setAlpha(1f);
            }

        }if (setTrans) {
            amount_of_stars_until_next_stage.setAlpha(0.5f);
            curr_amount_of_stars.setAlpha(0.5f);
            View header = rootView.findViewById(R.id.stage_header);
            header.setAlpha(0.75f);
            of12.setVisibility(View.INVISIBLE);
            curr_amount_of_stars.setVisibility(View.INVISIBLE);
            amount_of_stars_until_next_stage.setVisibility(View.INVISIBLE);
            first_cluster.setVisibility(View.INVISIBLE);
            View secondCluster = rootView.findViewById(R.id.level_select_bottom_startCluster2);
            secondCluster.setVisibility(View.INVISIBLE);
            View textBack = rootView.findViewById(R.id.level_select_stars_until_next_stage_background);
            textBack.setVisibility(View.INVISIBLE);
        }else{
            amount_of_stars_until_next_stage.setAlpha(1f);
            curr_amount_of_stars.setAlpha(1f);
            View header = rootView.findViewById(R.id.stage_header);
            header.setAlpha(1f);
            of12.setVisibility(View.VISIBLE);
            curr_amount_of_stars.setVisibility(View.VISIBLE);
            amount_of_stars_until_next_stage.setVisibility(View.VISIBLE);
            first_cluster.setVisibility(View.VISIBLE);
            View secondCluster = rootView.findViewById(R.id.level_select_bottom_startCluster2);
            secondCluster.setVisibility(View.VISIBLE);
            View textBack = rootView.findViewById(R.id.level_select_stars_until_next_stage_background);
            textBack.setVisibility(View.VISIBLE);
        }
    }

    private boolean isUnlocked(){
        return sharedPrefs.getBoolean(getUnlockStatusKey(),false);
    }

    private void configureBottomInfoBar(){

        View back = rootView.findViewById(R.id.level_select_stars_until_next_stage_background);
        back.setBackground(ContextCompat.getDrawable(getContext(),bottomTextHolder));
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

    protected int setStarsandScore(String[] keys, int[] stars, View rootView){
        int score = 0;
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
        int stars_needed = 3 - score;
        View second_cluster = rootView.findViewById(R.id.level_select_bottom_startCluster2);
        if (stars_needed >0) {
            amount_of_stars_until_next_stage.setText(getNextStageNum() + " In " + Integer.toString(stars_needed));
        }else{
            amount_of_stars_until_next_stage.setText(getNextStageNum() + " Unlocked!");
            amount_of_stars_until_next_stage.setTextSize(18);
            final AlphaAnimation animation = new AlphaAnimation(0.0f,1.0f);
            animation.setDuration(500);
            animation.setStartOffset(4500);
            animation.setRepeatCount(Animation.INFINITE);
            animation.setRepeatMode(Animation.REVERSE);
            second_cluster.startAnimation(animation);
        }
        return stars_needed;
    }

    protected void setTitleBar(){
        TextView header = (TextView) rootView.findViewById(R.id.stage_header);
        header.setText(getStageNum());
        header.setTypeface(typefaceFont);
    }

    protected void registerLevelSelectors(ArrayList<Button> levelSelectors,ArrayList<Intent> intents, boolean registerButtons) {
        for (int i = 0;i < intents.size();i++) {
            Button btn = (Button)levelSelectors.get(i);
            btn.setTypeface(typefaceFont);
            //btn.setTextColor(fontColor);
            if(registerButtons) {
                registerClickListeners(intents.get(i), btn);
            }
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

    protected abstract String getUnlockStatusKey();

    protected abstract String getNextStageUnlockStatusKey();
}
