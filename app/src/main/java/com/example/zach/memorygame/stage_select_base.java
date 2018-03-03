package com.example.zach.memorygame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
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
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

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
               R.id.level_1_stars, R.id.level_2_stars, R.id.level_3_stars, R.id.level_4_stars};
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
                rootView.findViewById(star).setVisibility(View.GONE);
            }else{
                rootView.findViewById(star).setVisibility(View.VISIBLE);
            }

        }if (setTrans) {
            amount_of_stars_until_next_stage.setAlpha(0.5f);
            curr_amount_of_stars.setAlpha(0.5f);
            View header = rootView.findViewById(R.id.stage_header);
            header.setAlpha(0.9f);
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
        curr_amount_of_stars.setText(Integer.toString(score));
        int stars_needed = 9 - score;
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
