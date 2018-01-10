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
                R.id.lvl1_bronze,R.id.lvl1_silver,R.id.lvl1_gold,R.id.lvl2_bronze,R.id.lvl2_silver,R.id.lvl2_gold,
                R.id.lvl3_bronze,R.id.lvl3_silver,R.id.lvl3_gold,R.id.lvl4_bronze,R.id.lvl4_silver,R.id.lvl4_gold};
        for (int i = 0; i<keys.length;i++){
            if (key.equals(keys[i]) && sharedPrefs.contains(key)){
                String defaultValue = getString(R.string.shared_pref_no_medal);
                View bronze_star = findViewById(stars[i]);
                View silver_star = findViewById(stars[i+1]);
                View gold_star = findViewById(stars[i+2]);
                if (sharedPrefs.getString(keys[i],defaultValue).equals(getString(R.string.shared_pref_gold))) {
                    gold_star.setVisibility(View.VISIBLE);
                    silver_star.setVisibility(View.VISIBLE);
                    bronze_star.setVisibility(View.VISIBLE);
                }
                if (sharedPrefs.getString(keys[i],defaultValue).equals(getString(R.string.shared_pref_silver))) {
                    silver_star.setVisibility(View.VISIBLE);
                    bronze_star.setVisibility(View.VISIBLE);
                }
                if (sharedPrefs.getString(keys[i],defaultValue).equals(getString(R.string.shared_pref_bronze))) {
                    bronze_star.setVisibility(View.VISIBLE);
                }
                if (sharedPrefs.getString(keys[i],defaultValue).equals(getString(R.string.shared_pref_no_medal))) {
                    gold_star.setVisibility(View.INVISIBLE);
                    silver_star.setVisibility(View.INVISIBLE);
                    bronze_star.setVisibility(View.INVISIBLE);
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
