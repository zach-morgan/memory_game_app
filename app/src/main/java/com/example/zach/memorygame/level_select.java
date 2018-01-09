package com.example.zach.memorygame;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.Arrays;

public class level_select extends AppCompatActivity {

    private ArrayList<View> levelSelectors;

    private ArrayList<Object> levels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);
        levelSelectors = new ArrayList<>(Arrays.asList(findViewById(R.id.stage1lvl1), findViewById(R.id.stage1lvl2),
                findViewById(R.id.stage1lvl3), findViewById(R.id.stage1lvl4)));
        for (View levelButton : levelSelectors){
            Intent intent = new Intent(this,levels.class);
            intent.putExtra("LevelNum","1L"+Integer.toString(levelSelectors.indexOf(levelButton)+1));
            registerClickListeners(intent,levelButton);
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
