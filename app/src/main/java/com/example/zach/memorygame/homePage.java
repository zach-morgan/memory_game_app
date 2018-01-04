package com.example.zach.memorygame;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class homePage extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private View smallBubble;

    private View medBubble;

    private View largeBubble;

    private View tile;

    private View playButton;

    private View themeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        smallBubble = findViewById(R.id.bubblesmall);
        medBubble = findViewById(R.id.bubblemed);
        largeBubble = findViewById(R.id.bubblelarge);
        tile = findViewById(R.id.tile_in_bubble);
        playButton = findViewById(R.id.playButton);
        themeButton = findViewById(R.id.themeButton);
        openingAnimations();
        addButtonListeners();
    }


    private void openingAnimations(){
        //thought bubble animation
        tile.setAlpha(0f);
        smallBubble.setAlpha(0f);
        medBubble.setAlpha(0f);
        largeBubble.setAlpha(0f);
        smallBubble.animate().alpha(1f).setDuration(4000).setListener(null);
        medBubble.animate().alpha(1f).setDuration(6000).setListener(null);
        largeBubble.animate().alpha(1f).setDuration(8000).setListener(null);
        tile.animate().alpha(1f).setDuration(8000).setListener(null);
        //Spring animations for buttons

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        return;
    }

    private void addButtonListeners(){
        final Intent playIntent = new Intent(this,level_select.class);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(playIntent);
            }
        });
    }
}
