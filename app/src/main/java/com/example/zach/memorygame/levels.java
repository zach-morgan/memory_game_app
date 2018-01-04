package com.example.zach.memorygame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class levels extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_base);
        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("LevelNum");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("LevelNum");
        }
        /**
        TextView test = findViewById(R.id.test);
        test.setText(newString);
*/

    }
}
