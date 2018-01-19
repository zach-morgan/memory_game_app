package com.example.zach.memorygame;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public abstract class levels extends AppCompatActivity implements Observer {

    protected static ConcentrationModel model;

    protected TextView TimeView,levelLabel, moveCounter,Move;

    protected View game_layout;

    protected static int[] images,buttons;

    private static ArrayList<Integer> cartoonImages;

    private static ArrayList<Button> buttonObjects;

    private static Timer timer;

    private static int Time_minutes,Time_seconds;

    private static boolean isTimerGoing;

    private AlertDialog quitAlertDialog;

    private boolean gameFinished = false;

    private boolean gameInPlay = false;

    protected String[] goals;

    private ViewGroup.LayoutParams layoutparams;

    private String levelkey;


    //ANDROID BASIC FUNCTIONS

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.level_base);
            initializeGlobalHeader();
            initializeImageArrays();
            initializeQuitAlertDialog();
            initializeGame();
            levelkey = getKey();
        }

        @Override
        public void onBackPressed() {
            pauseTimer();
            quitAlertDialog.show();
        }


    //END ANDROID BASIC FUNCTIONS

    //MEMORY MANAGEMENT




    //END MEMORY MANAGEMENT




    //----------------------------------------------------------------------------------------------

    //GENERAL INITS

        private void initializeGlobalHeader(){
            moveCounter = findViewById(R.id.move_counter);
            TimeView = findViewById(R.id.timer);
            View resetButton = findViewById(R.id.reset_button);
            Move = findViewById(R.id.move_label);
            levelLabel = findViewById(R.id.stage_level_label);
            moveCounter.setAlpha(0f);
            TimeView.setAlpha(0f);
            resetButton.setAlpha(0f);
            Move.setAlpha(0f);
            levelLabel.setAlpha(0f);
            isTimerGoing = false;
            Time_minutes = 0;
            Time_seconds = 0;
            timer = new Timer();
            resetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    model.reset();
                    resetTimer();
                }
            });
        }

        protected void initializeLevelIntroduction(String[] params){
            final LinearLayout mainLayout = findViewById(R.id.mainLayout);
            final View level_intro = getLayoutInflater().inflate(R.layout.level_introduction,mainLayout,false);
            mainLayout.addView(level_intro);
            TextView bronze_time = findViewById(R.id.bronze_time);
            TextView bronze_moves = findViewById(R.id.bronze_moves);
            TextView silver_time = findViewById(R.id.silver_time);
            TextView silver_moves = findViewById(R.id.silver_moves);
            TextView gold_time = findViewById(R.id.gold_time);
            TextView gold_moves = findViewById(R.id.gold_moves);
            gold_time.setText(params[0]);
            gold_moves.setText(params[1] + " Moves");
            silver_time.setText(params[2]);
            silver_moves.setText(params[3] + " Moves");
            bronze_time.setText(params[4]);
            bronze_moves.setText(params[5] + " Moves");
            Button btn = (Button)findViewById(R.id.intro_playButton);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TransitionManager.beginDelayedTransition(mainLayout);
                    layoutparams = level_intro.getLayoutParams();
                    layoutparams.height = 0;
                    level_intro.setLayoutParams(layoutparams);
                    mainLayout.addView(game_layout);
                    animateHeader();
                    playGame();
                }
            });

        }

        private void initializeImageArrays(){
            buttonObjects = new ArrayList<>();
            cartoonImages = new ArrayList<>(Arrays.asList(
                    R.drawable.cartoon_tileflipped_whale,R.drawable.cartoon_tileflipped_cloud,R.drawable.cartoon_tileflipped_cuteprince,R.drawable.cartoon_tileflipped_punkguy
                    ,R.drawable.cartoon_elephant,R.drawable.cartoon_tileflipped_computerguy,R.drawable.cartoon_tileflipped_mexicanguy,R.drawable.cartoon_tileflipped_octopus,
                    R.drawable.cartoon_tileflipped_world
            ));
        }

        private void initializeQuitAlertDialog(){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setCancelable(false);
            alertBuilder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    resetTimer();
                    levels.super.onBackPressed();
                }
            });
            alertBuilder.setNegativeButton("Don't Quit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    quitAlertDialog.cancel();
                    if (!gameFinished && gameInPlay) {
                        resumeTimer();
                    }
                }
            });
            alertBuilder.setMessage("Are you sure you want to quit?\n(You will lose your progress for this level)");
            quitAlertDialog = alertBuilder.create();
        }


        protected void loadImages(int numImages, String theme){
            images = new int[numImages];
            Random rand = new Random();
            for (int i=0;i < numImages;i++){
                switch(theme){
                    case "Cartoon":
                        int randomNum = rand.nextInt(cartoonImages.size());
                        images[i] = cartoonImages.get(randomNum);
                        cartoonImages.remove(randomNum);
                }
            }
        }

        protected void registersGameCards(int[] buttonIDs){
            Button btn;
            for (int i = 0;i < buttonIDs.length;i++){
                btn =(Button)findViewById(buttonIDs[i]);
                setButtonListener(btn,i);
                buttonObjects.add(btn);
            }
        }

        private void setButtonListener(Button btn,final int index){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    model.selectCard(index);
                }
            });
        }
    //END GENERAL INITS

    //----------------------------------------------------------------------------------------------

    //OBSERVER UPDATES

        @Override
        public void update(Observable observable, Object o) {
            if (o == null) {
                gameInPlay = true;
                updateBoard(model.getCards());
                if (checkWin() == 0) {
                    gameFinished = true;
                    gameInPlay = false;
                    timer.cancel();
                    timer.purge();
                    model.deleteObserver(this);
                    model = null;
                    winScreen();

                }
                if (!isTimerGoing) {
                    startTimer();
                }
            }
            else if (o.equals("reset")){
                resetTimer();
                gameInPlay = false;
                isTimerGoing = false;
                updateBoard(model.getCards());
            }
        }

        private void updateBoard(ArrayList<Card> cards){
            for (int i=0;i < buttons.length;i++){
                int cardNum = cards.get(i).getNumber();
                if(cardNum == -1){
                    setBackground(buttonObjects.get(i),R.drawable.cartoon_tileback);
                }
                else{
                    setBackground(buttonObjects.get(i),images[cardNum]);
                }
            }
            moveCounter.setText(Integer.toString(model.getMoveCount()));
        }

        private void setBackground(final Button btn, int background){
            Glide.with(this).load(getDrawable(background)).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    btn.setBackground(resource);
                }
            });
        }


    //END OBSERVER UPDATES

    //----------------------------------------------------------------------------------------------

    //WIN RELATED FUNCTIONS

        private int checkWin(){
            int count = 0;
            for (Card card : model.getCards()){
                if (!card.isFaceUp()){
                    count++;
                }
            }
            return count;
        }


        private void winScreen(){
            //switch from game header to You Won!
            initializeWinScreenHeader();
            //transition between game layout and trophy screen
            LinearLayout mainLayout = findViewById(R.id.mainLayout);
            View winScreen_layout = getLayoutInflater().inflate(R.layout.trophy_screen,mainLayout,false);
            TransitionManager.beginDelayedTransition(mainLayout);
            layoutparams = game_layout.getLayoutParams();
            layoutparams.height = 0;
            game_layout.setLayoutParams(layoutparams);
            mainLayout.addView(winScreen_layout);
            mainLayout.removeView(game_layout);
            String medal = level_of_win();
            configureWin(medal);
            registerEndofgameButtons();
        }


        private void registerEndofgameButtons(){
            final Button level_select = (Button) findViewById(R.id.back_to_level_select_button);
            final Button next_level = (Button)findViewById(R.id.next_level_button);
            final Intent back_to_level_select = new Intent(this,level_select.class);
            level_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    levels.super.onBackPressed();
                    finish();
                }
            });
            final Intent next_level_intent = getNextLevelIntent();
            next_level.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(next_level_intent);
                    finish();
                }
            });
        }

        private void configureWin(String medal){
            ImageView gold_trophy = findViewById(R.id.gold_trophy_win);
            ImageView silver_trophy = findViewById(R.id.silver_trophy_win);
            ImageView bronze_trophy = findViewById(R.id.bronze_trophy_win);
            ImageView no_medal = findViewById(R.id.no_medal_win);

            switch(medal){
                case "gold":
                    gold_trophy.setVisibility(View.VISIBLE);
                    String goldValue = getString(R.string.shared_pref_gold);
                    saveMedal(goldValue);
                    //animateTrophy(gold_trophy);
                    break;
                case "silver":
                    silver_trophy.setVisibility(View.VISIBLE);
                    String silverValue = getString(R.string.shared_pref_silver);
                    saveMedal(silverValue);
                    break;
                //animateTrophy(silver_trophy);
                case "bronze":
                    bronze_trophy.setVisibility(View.VISIBLE);
                    String bronzeValue = getString(R.string.shared_pref_bronze);
                    saveMedal(bronzeValue);
                    break;
                //animateTrophy(bronze_trophy);
                case "no_medal":
                    no_medal.setVisibility(View.VISIBLE);
                    TextView final_time_view = findViewById(R.id.final_time);
                    TextView final_moves_view = findViewById(R.id.final_moves);
                    View win_border = findViewById(R.id.win_border);
                    win_border.setVisibility(View.INVISIBLE);
                    final_time_view.setVisibility(View.INVISIBLE);
                    final_moves_view.setText("No Medal");
                    String no_medalValue = getString(R.string.shared_pref_no_medal);
                    saveMedal(no_medalValue);
                    //do something else

            }
        }

        private void saveMedal(String medalValue){
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPref.edit();
            String default_value = getString(R.string.shared_pref_no_medal);
            int new_medalLevel = Integer.parseInt(medalValue);
            int current_medalLevel = Integer.parseInt(sharedPref.getString(levelkey,default_value));
            if (new_medalLevel > current_medalLevel){
                editor.putString(levelkey,medalValue);
            }
            editor.apply();
        }


        private void initializeWinScreenHeader() {
            View victory_header = findViewById(R.id.you_won);
            View left_ribbon = findViewById(R.id.left_ribbon);
            View right_ribbon = findViewById(R.id.right_ribbon);
            ArrayList<View> win_views = new ArrayList<>(Arrays.asList(victory_header, left_ribbon, right_ribbon));
            TextView Move = findViewById(R.id.move_label);
            View resetButton = findViewById(R.id.reset_button);
            ViewGroup levelheader = findViewById(R.id.level_header);
            ArrayList<View> views = new ArrayList<>(Arrays.asList(Move, resetButton, TimeView, levelLabel, moveCounter));
            Move.animate().alpha(0f).setDuration(1000).setListener(new animatorListener(levelheader, views));
            for (View view : views) {
                view.animate().alpha(0f).setDuration(1000).setListener(null);
            }
            for (View win : win_views) {
                win.setVisibility(View.VISIBLE);
                win.setAlpha(0f);
                win.animate().alpha(1f).setDuration(1000).setListener(null);
            }
        }

        /**
         * FIX THIS SHIT
         * @param trophy
         */
        private void animateTrophy(final ImageView trophy){
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)trophy.getLayoutParams();
            params.setMargins(0,0,-1 * trophy.getWidth(),0);
            trophy.setLayoutParams(params);
            Animation animation = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)trophy.getLayoutParams();
                    params.setMargins(0,0,0,0);
                    trophy.setLayoutParams(params);
                }
            };
            animation.setDuration(2000);
            trophy.startAnimation(animation);
        }


        private String level_of_win(){
            int final_time = Time_minutes + Time_seconds;
            int final_moveCount = Integer.parseInt(moveCounter.getText().toString());
            TextView final_time_view = findViewById(R.id.final_time);
            TextView final_moves_view = findViewById(R.id.final_moves);
            if (Time_seconds <10){
                setTime(Integer.toString(Time_minutes) + ":0" + Integer.toString(Time_seconds),final_time_view);
            }else {
                setTime(Integer.toString(Time_minutes) + ":" + Integer.toString(Time_seconds),final_time_view);
            }
            final_moves_view.setText(final_moveCount + " Moves");
            for (int i =0; i <goals.length;i+=2){
                String time_string = goals[i];
                int time = Integer.parseInt(time_string.substring(0,1)) + Integer.parseInt(time_string.substring(2,4));
                int moveCount = Integer.parseInt(goals[i+1]);
                if (final_time <= time && final_moveCount <= moveCount){
                    switch(i){
                        case 0:
                            return "gold";
                        case 2:
                            return "silver";
                        case 4:
                            return "bronze";
                    }
                }
            }
            return "no_medal";
        }

    //END WIN RELATED FUNCTIONS

    private void animateHeader(){
        TextView Move = findViewById(R.id.move_label);
        View resetButton = findViewById(R.id.reset_button);
        Move.animate().alpha(1f).setDuration(1500).setListener(null);
        TimeView.animate().alpha(1f).setDuration(1500).setListener(null);
        levelLabel.animate().alpha(1f).setDuration(1500).setListener(null);
        resetButton.animate().alpha(1f).setDuration(1500).setListener(null);
        moveCounter.animate().alpha(1f).setDuration(1500).setListener(null);
    }

    //----------------------------------------------------------------------------------------------

    //TIMER UTILITY FUNCTIONS


        private void startTimer(){
            isTimerGoing = true;
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (Time_seconds + 1 == 60){
                        Time_minutes++;
                        Time_seconds = 0;
                    }else{
                        Time_seconds++;
                    }
                    mHandler.obtainMessage(1).sendToTarget();
                }
            },0,1000);
        }

        private void pauseTimer(){
            timer.cancel();
        }

        private void resumeTimer(){
            startTimer();
        }

        private void resetTimer(){
            timer.cancel();
            timer.purge();
            Time_minutes =0;
            Time_seconds =0;
            TimeView.setText("0:00");
        }

        public Handler mHandler = new Handler(){
            public void handleMessage(Message msg){
                if (Time_seconds <10){
                    setTime(Integer.toString(Time_minutes) + ":0" + Integer.toString(Time_seconds),TimeView);
                }else {
                    setTime(Integer.toString(Time_minutes) + ":" + Integer.toString(Time_seconds),TimeView);
                }
            }
        };

        private void setTime(String time, TextView view){
            view.setText(time);
        }

    //END TIMER UTILITY FUNCTIONS

    //----------------------------------------------------------------------------------------------

    //ABSTRACT FUNCTIONS

        protected abstract void playGame();

        protected abstract void initializeGame();

        protected abstract String getKey();

        protected abstract Intent getNextLevelIntent();
    //END ABSTRACT FUNCTIONS
}
