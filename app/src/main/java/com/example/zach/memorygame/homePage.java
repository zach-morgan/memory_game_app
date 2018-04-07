package com.example.zach.memorygame;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Arrays;

public class homePage extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{


    private SharedPreferences sharedPrefs;

    int[] michaelBubleDrawables;

    int person,theme,buttonBackground, fontColor;

    Typeface typefaceFont;

    public MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPrefs.registerOnSharedPreferenceChangeListener(this);
        michaelBubleDrawables = new int[]{R.drawable.firstdot,R.drawable.seconddot,R.drawable.thirddot,R.drawable.cartoon_tileback};
        configureTheme();
        setTheme(theme);
        backgroundMusic = MediaPlayer.create(getApplicationContext(), R.raw.backgroundmusic);
        setContentView(R.layout.activity_home_page);
        backgroundMusic.setLooping(true);
        if (sharedPrefs.getBoolean(getString(R.string.isMuted_key),true)) {
            backgroundMusic.start();
            FloatingActionButton muteButton = findViewById(R.id.mute_button);
            muteButton.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.muted));
        }else{
            FloatingActionButton muteButton = findViewById(R.id.mute_button);
            muteButton.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.unmuted));
        }
        ImageView Person = (ImageView) findViewById(R.id.person);
        Glide.with(this).load(ContextCompat.getDrawable(this,person)).into(Person);
        ArrayList<ImageView> michaelBuble = new ArrayList<>(Arrays.asList((ImageView) findViewById(R.id.bubblesmall),
                (ImageView) findViewById(R.id.bubblemed), (ImageView) findViewById(R.id.bubblelarge), (ImageView) findViewById(R.id.tile_in_bubble)));
        openingAnimations(michaelBuble);
        addButtonListeners();
        initializeSharedPrefs();
        MobileAds.initialize(this,"ca-app-pub-6169106962153795~4127284569");
        AdView mAdView = findViewById(R.id.bannerAd);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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
                font = R.font.sriracha;
                typefaceFont = ResourcesCompat.getFont(this,font);
                fontColor = R.color.murica_flag_blue;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (backgroundMusic.isPlaying()){
            backgroundMusic.release();
        }
    }

    private void initializeSharedPrefs(){
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(getString(R.string.shared_pref_stage1_lock_status_key),true);
        editor.apply();
    }

    private void openingAnimations(ArrayList<ImageView> bubbleViews){
        //thought bubble animation
        int animationTime = 2000;
        for (int i = 0;i < michaelBubleDrawables.length;i++){
            ImageView daCurrentView = bubbleViews.get(i);
            Glide.with(this).load(ContextCompat.getDrawable(this,michaelBubleDrawables[i])).into(daCurrentView);
            daCurrentView.setAlpha(0f);
            if (animationTime == 7000){
                animationTime = 6000;
            }
            daCurrentView.animate().alpha(1f).setDuration(animationTime).setListener(null);
            animationTime+=750;
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
        final FloatingActionButton muteButton = findViewById(R.id.mute_button);
        final Toast unMutetoast = Toast.makeText(this, "All Sounds UnMuted", Toast.LENGTH_SHORT);
        ViewGroup toastLayout = (ViewGroup) unMutetoast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(12);
        final Toast mutetoast = Toast.makeText(this, "All Sounds Muted", Toast.LENGTH_SHORT);
        ViewGroup toastLayout2 = (ViewGroup) mutetoast.getView();
        TextView toastTV2 = (TextView) toastLayout2.getChildAt(0);
        toastTV2.setTextSize(12);
        muteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean muteValue = sharedPrefs.getBoolean(getString(R.string.isMuted_key),false);
                SharedPreferences.Editor ed = sharedPrefs.edit();
                ed.putBoolean(getString(R.string.isMuted_key),!muteValue);
                if (!muteValue){
                    muteButton.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.muted));
                    unMutetoast.show();
                }else{
                    muteButton.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.unmuted));
                    mutetoast.show();
                }
                ed.apply();
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String muteK = getString(R.string.isMuted_key);
        if (key.equals(muteK)){
            if (sharedPrefs.getBoolean(muteK,true)){
                backgroundMusic.start();
            }else{
                if (backgroundMusic.isPlaying()) {
                    backgroundMusic.pause();
                }
            }
        }
    }
}
