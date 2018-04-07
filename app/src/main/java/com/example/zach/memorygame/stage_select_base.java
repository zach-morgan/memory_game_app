package com.example.zach.memorygame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Zach on 1/16/2018.
 */

public abstract class stage_select_base extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    protected View rootView;

    protected ViewPager pager;

    protected String[] keys;

    protected int[] stars;

    protected Typeface typefaceFont;

    int theme,lockedLevel, bodyBackground, fontColor, rcBack, rcProgbar;

    ArrayList<Intent> intents;

    SharedPreferences sharedPrefs;

    ArrayList<Button> levelSelectors;

    protected static Animation nextStageanimation;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState
                            , String[] keys, ArrayList<Intent> intents) {
        super.onCreate(savedInstanceState);
        this.keys = keys;
        this.intents = intents;
        stars = new int[] {
               R.id.level_1_stars, R.id.level_2_stars, R.id.level_3_stars, R.id.level_4_stars};
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        setTheme();
        rootView = inflater.inflate(R.layout.activity_level_select,container,false);
        setTitleBar();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ImageView background = rootView.findViewById(R.id.level_select_buttons_background);
        background.setBackground(ContextCompat.getDrawable(getContext(),bodyBackground));
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
                final Button swipeIndicate = rootView.findViewById(R.id.swipeindicator);
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
                        next_stage_popup.setVisibility(View.GONE);
                        swipeIndicate.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                next_stage_popup.setAnimation(nextStageanimation);
                swipeIndicate.setVisibility(View.VISIBLE);
                swipeIndicate.setAnimation(nextStageanimation);
                editor.putBoolean(getNextStageUnlockStatusKey(),true);
                editor.apply();
            }
        }
        if (isUnlocked()){
            setBackgroundTransparent(false);
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
                fontColor = R.color.cartoon_Text;
                rcBack = R.color.cartoon_whitePrimary;
                rcProgbar = R.color.cartoon_colorPrimary;
                break;
            case "murica":
                font = R.font.sriracha;
                typefaceFont = ResourcesCompat.getFont(getContext(),font);
                theme = R.style.murica_levels_select;
                lockedLevel = R.drawable.murica_lock;
                bodyBackground = R.drawable.murica_level_select_background;
                fontColor = R.color.murica_flag_blue;
                rcBack = R.color.murica_flag_blue;
                rcProgbar = R.color.murica_flag_red;
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
                rootView.findViewById(star).setVisibility(View.GONE);
            }else{
                rootView.findViewById(star).setVisibility(View.VISIBLE);
            }

        }if (setTrans) {
            View header = rootView.findViewById(R.id.stage_header);
            header.setAlpha(0.9f);
        }else{
            View header = rootView.findViewById(R.id.stage_header);
            header.setAlpha(1f);
        }
    }

    private boolean isUnlocked(){
        return sharedPrefs.getBoolean(getUnlockStatusKey(),false);
    }


    protected int setStarsandScore(String[] keys, int[] stars, View rootView){
        int score = 0;
        String defaultValue = getString(R.string.shared_pref_no_medal);
        String goldValue = getString(R.string.shared_pref_gold);
        String silverValue = getString(R.string.shared_pref_silver);
        String bronzeValue = getString(R.string.shared_pref_bronze);
        int goldStar = R.drawable.level_select_gold;
        int silverStar = R.drawable.level_select_silver;
        int bronzeStar = R.drawable.level_select_bronze;
        int noStar = R.drawable.level_select_no_medal;
        for (int i = 0; i<keys.length;i++) {
            Button star_holder = rootView.findViewById(stars[i]);
            String savedVal = sharedPrefs.getString(keys[i], defaultValue);
            String[] valueArray = savedVal.trim().split(",");
            String value = valueArray[0];
            if (value.equals(goldValue)){
                setBackgroundHelper(star_holder, goldStar );
                //star_holder.setBackground(ContextCompat.getDrawable(getContext(),goldStar));
                score += 3;
            }
            if (value.equals(silverValue)){
                setBackgroundHelper(star_holder,silverStar);
                //star_holder.setBackground(ContextCompat.getDrawable(getContext(),silverStar));
                score += 2;
            }
            if (value.equals(bronzeValue)){
                setBackgroundHelper(star_holder,bronzeStar);
                //star_holder.setBackground(ContextCompat.getDrawable(getContext(),bronzeStar));
                score += 1;
            }
            if (value.equals(defaultValue)){
                //star_holder.setBackground(ContextCompat.getDrawable(getContext(),noStar));
                setBackgroundHelper(star_holder,noStar);
            }
        }
        int stars_needed = 9 - score;
        configureProgressBar(score);
        /**
        if (stars_needed >0) {

        }else{

        }
         */
        return stars_needed;
    }

    public void configureProgressBar(int numOfStars){
        RoundCornerProgressBar progBar = (RoundCornerProgressBar) rootView.findViewById(R.id.progBar);
        progBar.setProgressBackgroundColor(getResources().getColor(rcBack));
        progBar.setProgressColor(getResources().getColor(rcProgbar));
        TextView overlayText = rootView.findViewById(R.id.progBarTextOverlay);
        setFont(overlayText, typefaceFont);
        if (numOfStars < 9){
            overlayText.setText("Next Stage Progress");
            progBar.setMax(9);
        }else{
            progBar.setMax(12);
            if (numOfStars == 12){
                overlayText.setText("Perfect Stage!");
            }else{
                overlayText.setText("Total Stars Progress");
            }
        }
        progBar.setProgress(numOfStars);

    }

    private void setBackgroundHelper(final View btn, int drawable) {
        Glide.with((Fragment)this).load(ContextCompat.getDrawable(getContext(), drawable)).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                btn.setBackground(resource);
            }
        });
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
