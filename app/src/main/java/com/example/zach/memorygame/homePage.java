package com.example.zach.memorygame;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;

public class homePage extends AppCompatActivity{


    private SharedPreferences sharedPrefs;

    int[] michaelBubleDrawables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ImageView Person = (ImageView) findViewById(R.id.person);
        Glide.with(this).load(getDrawable(R.drawable.person)).into(Person);
        ArrayList<ImageView> michaelBuble = new ArrayList<>(Arrays.asList((ImageView) findViewById(R.id.bubblesmall),
        (ImageView) findViewById(R.id.bubblemed), (ImageView) findViewById(R.id.bubblelarge), (ImageView) findViewById(R.id.tile_in_bubble)));
        michaelBubleDrawables = new int[]{R.drawable.firstdot,R.drawable.seconddot,R.drawable.thirddot,R.drawable.cartoon_tileback};
        configureTheme();
        openingAnimations(michaelBuble);
        addButtonListeners();
        initializeSharedPrefs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTheme(R.style.cartoon_HomePage);
    }

    private void configureTheme(){
        String theme = sharedPrefs.getString(getString(R.string.shared_pref_theme_key),"cartoon");
        switch(theme){
            case "cartoon":
                michaelBubleDrawables[3] = R.drawable.cartoon_tileback;
                setTheme(R.style.cartoon_HomePage);
        }
    }

    private void initializeSharedPrefs(){
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(getString(R.string.shared_pref_stage1_lock_status_key),true);
        editor.commit();
    }

    private void openingAnimations(ArrayList<ImageView> bubbleViews){
        //thought bubble animation
        int animationTime = 4000;
        for (int i = 0;i < michaelBubleDrawables.length;i++){
            ImageView daCurrentView = bubbleViews.get(i);
            Glide.with(this).load(getDrawable(michaelBubleDrawables[i])).into(daCurrentView);
            daCurrentView.setAlpha(0f);
            if (animationTime == 7000){
                animationTime = 6000;
            }
            daCurrentView.animate().alpha(1f).setDuration(animationTime).setListener(null);
            animationTime+=1000;
        }
        //Spring animations for buttons

    }

    private void addButtonListeners(){
        View playButton = findViewById(R.id.playButton);
        View themeButton = findViewById(R.id.themeButton);
        final Intent playIntent = new Intent(this,level_select.class);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(playIntent);
            }
        });
    }

}
