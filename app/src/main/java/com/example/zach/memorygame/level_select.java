package com.example.zach.memorygame;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;

public class level_select extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);
        ArrayList<View> levelSelectors = new ArrayList<>(Arrays.asList(findViewById(R.id.stage1lvl1), findViewById(R.id.stage1lvl2),
                findViewById(R.id.stage1lvl3), findViewById(R.id.stage1lvl4)));
        sharedPreferencesStuff();
        for (View levelButton : levelSelectors){
            Intent intent = null;
            switch(levelSelectors.indexOf(levelButton)) {
                case 0:
                    intent = new Intent(this, S1L1.class);
                    break;
                case 1:
                    intent = new Intent(this, S1L2.class);
                    break;
                case 2:
                    intent = new Intent(this, S1L3.class);
                    break;
                case 3:
                    intent = new Intent(this, S1L4.class);
                    break;
            }
            if (intent == null){
                continue;
            }
            registerClickListeners(intent,levelButton);
        }
    }

    private void sharedPreferencesStuff(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPref.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPrefs, String key) {
        String[] keys = new String[]{getString(R.string.shared_pref_S1L1_key),"","",getString(R.string.shared_pref_S1L2_key),
                "","",getString(R.string.shared_pref_S1L3_key),"","",getString(R.string.shared_pref_S1L4_key)};
        int[] stars = new int[] {
                R.id.lvl1_bronze_place,R.id.lvl1_silver_place,R.id.lvl1_gold_place,R.id.lvl2_bronze_place,R.id.lvl2_silver_place,R.id.lvl2_gold_place,
                R.id.lvl3_bronze_place,R.id.lvl3_silver_place,R.id.lvl3_gold_place,R.id.lvl4_bronze_place,R.id.lvl4_silver_place,R.id.lvl4_gold_place};
        for (int i = 0; i<keys.length;i++){
            if (key.equals(keys[i]) && sharedPrefs.contains(key)){
                String defaultValue = getString(R.string.shared_pref_no_medal);
                ImageView bronze_star = findViewById(stars[i]);
                ImageView silver_star = findViewById(stars[i+1]);
                ImageView gold_star = findViewById(stars[i+2]);
                if (sharedPrefs.getString(keys[i],defaultValue).equals(getString(R.string.shared_pref_gold))) {
                    Glide.with(this).load(getDrawable(R.drawable.level_select_gold_star)).into(gold_star);
                    Glide.with(this).load(getDrawable(R.drawable.level_select_silver_star)).into(silver_star);
                    Glide.with(this).load(getDrawable(R.drawable.level_select_bronze_star)).into(bronze_star);
                }
                if (sharedPrefs.getString(keys[i],defaultValue).equals(getString(R.string.shared_pref_silver))) {
                    Glide.with(this).load(getDrawable(R.drawable.level_select_silver_star)).into(silver_star);
                    Glide.with(this).load(getDrawable(R.drawable.level_select_bronze_star)).into(bronze_star);
                }
                if (sharedPrefs.getString(keys[i],defaultValue).equals(getString(R.string.shared_pref_bronze))) {
                    Glide.with(this).load(getDrawable(R.drawable.level_select_bronze_star)).into(bronze_star);
                }
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
}
