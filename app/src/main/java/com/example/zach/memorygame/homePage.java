package com.example.zach.memorygame;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class homePage extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {


    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ImageView person =  findViewById(R.id.person);
        Glide.with(this).load(getDrawable(R.drawable.person)).into(person);
        ImageView smallBubble = findViewById(R.id.bubblesmall);
        Glide.with(this).load(getDrawable(R.drawable.firstdot)).into(smallBubble);
        ImageView medBubble = findViewById(R.id.bubblemed);
        Glide.with(this).load(getDrawable(R.drawable.seconddot)).into(medBubble);
        ImageView largeBubble = findViewById(R.id.bubblelarge);
        Glide.with(this).load(getDrawable(R.drawable.thirddot)).into(largeBubble);
        ImageView tile = findViewById(R.id.tile_in_bubble);
        Glide.with(this).load(getDrawable(R.drawable.cartoon_tileback)).into(tile);
        tile.setAlpha(0f);
        smallBubble.setAlpha(0f);
        medBubble.setAlpha(0f);
        largeBubble.setAlpha(0f);
        smallBubble.animate().alpha(1f).setDuration(4000).setListener(null);
        medBubble.animate().alpha(1f).setDuration(6000).setListener(null);
        largeBubble.animate().alpha(1f).setDuration(8000).setListener(null);
        tile.animate().alpha(1f).setDuration(8000).setListener(null);
        addButtonListeners();
        initializeSharedPrefs();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void initializeSharedPrefs(){
        sharedPrefs = this.getSharedPreferences(getString(R.string.medal_storage_master_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        String S1L1_key,S1L2_key,S1L3_key,S1L4_key,no_medal_value;
        S1L1_key = getString(R.string.shared_pref_S1L1_key);
        S1L2_key = getString(R.string.shared_pref_S1L2_key);
        S1L3_key = getString(R.string.shared_pref_S1L3_key);
        S1L4_key = getString(R.string.shared_pref_S1L4_key);
        no_medal_value = getString(R.string.shared_pref_no_medal);
        String[] keys = new String[]{S1L1_key,S1L2_key,S1L3_key,S1L4_key};
        for (String key : keys){
            if (!sharedPrefs.contains(key)){
                editor.putString(key,no_medal_value);
            }
        }
        editor.commit();
    }

    private void openingAnimations(){
        //thought bubble animation

        //Spring animations for buttons

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        return;
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
