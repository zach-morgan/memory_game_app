package com.example.zach.memorygame;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;

public class homePage extends AppCompatActivity{


    private SharedPreferences sharedPrefs;

    int[] michaelBubleDrawables;

    int person,theme,buttonBackground, fontColor;

    Typeface typefaceFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        michaelBubleDrawables = new int[]{R.drawable.firstdot,R.drawable.seconddot,R.drawable.thirddot,R.drawable.cartoon_tileback};
        configureTheme();
        setTheme(theme);
        setContentView(R.layout.activity_home_page);
        ImageView Person = (ImageView) findViewById(R.id.person);
        Glide.with(this).load(ContextCompat.getDrawable(this,person)).into(Person);
        ArrayList<ImageView> michaelBuble = new ArrayList<>(Arrays.asList((ImageView) findViewById(R.id.bubblesmall),
                (ImageView) findViewById(R.id.bubblemed), (ImageView) findViewById(R.id.bubblelarge), (ImageView) findViewById(R.id.tile_in_bubble)));
        openingAnimations(michaelBuble);
        addButtonListeners();
        initializeSharedPrefs();
    }

    private void configureTheme(){
        String themeString = sharedPrefs.getString(getString(R.string.shared_pref_theme_key),"cartoon");
        int font;
        switch(themeString){
            case "cartoon":
                michaelBubleDrawables[3] = R.drawable.cartoon_tileback;
                theme = R.style.cartoon_HomePage;
                person = R.drawable.person;
                buttonBackground = R.drawable.cartoon_button;
                font = R.font.finger_paint;
                typefaceFont = ResourcesCompat.getFont(this,font);
                fontColor = R.color.cartoon_Text;
                break;
            case "murica":
                michaelBubleDrawables[3] = R.drawable.murica_cardback;
                theme = R.style.murica_HomePage;
                person = R.drawable.murica_homepage_person_thinking;
                buttonBackground = R.drawable.murica_button;
                font = R.font.black_ops_one;
                typefaceFont = ResourcesCompat.getFont(this,font);
                fontColor = R.color.murica_flag_blue;
        }
    }

    private void initializeSharedPrefs(){
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(getString(R.string.shared_pref_stage1_lock_status_key),true);
        editor.apply();
    }

    private void openingAnimations(ArrayList<ImageView> bubbleViews){
        //thought bubble animation
        int animationTime = 4000;
        for (int i = 0;i < michaelBubleDrawables.length;i++){
            ImageView daCurrentView = bubbleViews.get(i);
            Glide.with(this).load(ContextCompat.getDrawable(this,michaelBubleDrawables[i])).into(daCurrentView);
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
        Button playButton = findViewById(R.id.playButton);
        Button themeButton = findViewById(R.id.themeButton);
        playButton.setBackground(ContextCompat.getDrawable(this,buttonBackground));
        playButton.setTypeface(typefaceFont);
        //playButton.setTextColor(fontColor);
        themeButton.setBackground(ContextCompat.getDrawable(this,buttonBackground));
        themeButton.setTypeface(typefaceFont);
        //themeButton.setTextColor(fontColor);
        final Intent playIntent = new Intent(this,level_select.class);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(playIntent);
            }
        });
        final Intent themeIntent = new Intent(this,theme_main.class);
        themeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(themeIntent);
            }
        });
    }

}
