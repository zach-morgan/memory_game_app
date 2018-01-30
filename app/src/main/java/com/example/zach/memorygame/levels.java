package com.example.zach.memorygame;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import java.util.zip.Inflater;

public abstract class levels extends AppCompatActivity implements Observer {

    protected static ConcentrationModel model;

    protected TextView TimeView,levelLabel, moveCounter,Move;

    protected View game_layout;

    protected static int[] images,buttons;

    private static ArrayList<Integer> cartoonImages, muricaImages;

    private static ArrayList<Button> buttonObjects;

    private static Timer timer;

    private static int Time_minutes,Time_seconds,tileBack;

    private static boolean isTimerGoing;

    private Dialog quitAlertDialog;

    private boolean gameFinished = false;

    private boolean gameInPlay = false;

    protected String[] goals;

    private ViewGroup.LayoutParams layoutparams;

    private String levelkey, Theme;

    protected boolean hasFlip;

    protected int flipIntervals, cardFlipTimeUp;

    Timer cardsFlippedTimer;


    //Theme Globals

    int level_background, navigation_button_background,font,popUpBackground,header_background,font_color;


    //ANDROID BASIC FUNCTIONS

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            themeConfiguration();
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


        private void themeConfiguration(){
            LinearLayout base = findViewById(R.id.mainLayout);
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            Theme = sharedPrefs.getString(getString(R.string.shared_pref_theme_key),"cartoon");
            switch(Theme){
                case "cartoon":
                    setTheme(R.style.cartoon_level_base);
                    level_background = R.drawable.cartoon_level_border;
                    navigation_button_background = R.drawable.cartoon_button;
                    font = R.font.finger_paint;
                    popUpBackground = R.drawable.cartoon_popup_text_holder;
                    header_background = R.drawable.cartoon_level_header;
                    font_color = R.color.cartoon_Text;
                    tileBack = R.drawable.cartoon_tileback;
                    break;
                case "murica":
                    setTheme(R.style.murica_level_base);
                    header_background = R.drawable.murica_text_holder;
                    font_color = R.color.murica_flag_blue;
                    font = R.font.black_ops_one;
                    navigation_button_background = R.drawable.murica_button;
                    header_background = R.drawable.murica_level_header;
                    level_background = R.drawable.murica_level_background;
                    tileBack = R.drawable.murica_cardback;
            }
        }


    //END ANDROID BASIC FUNCTIONS

    //MEMORY MANAGEMENT




    //END MEMORY MANAGEMENT




    //----------------------------------------------------------------------------------------------

    //GENERAL INITS

        private void initializeGlobalHeader(){
            moveCounter = findViewById(R.id.move_counter);
            moveCounter.setTextColor(ContextCompat.getColor(this,R.color.white));
            TimeView = findViewById(R.id.timer);
            TimeView.setTextColor(ContextCompat.getColor(this,R.color.white));
            View resetButton = findViewById(R.id.reset_button);
            Move = findViewById(R.id.move_label);
            Move.setTextColor(ContextCompat.getColor(this,R.color.white));
            levelLabel = findViewById(R.id.stage_level_label);
            levelLabel.setTextColor(ContextCompat.getColor(this,R.color.white));
            View header = findViewById(R.id.level_header);
            header.setBackground(ContextCompat.getDrawable(this,header_background));
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
            level_intro.setBackground(ContextCompat.getDrawable(this,level_background));
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
            btn.setBackground(ContextCompat.getDrawable(this,navigation_button_background));
            btn.setTypeface(ResourcesCompat.getFont(this,font));
            btn.setTextColor(ContextCompat.getColor(this,font_color));
            game_layout.setBackground(ContextCompat.getDrawable(this,level_background));
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
                    //updateBoard(model.getCards());
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
            muricaImages = new ArrayList<>(Arrays.asList(
               R.drawable.murica_tileflipped_california,R.drawable.murica_tileflipped_newyork,R.drawable.murica_tileflipped_idaho,
                    R.drawable.murica_tileflipped_florida, R.drawable.murica_tileflipped_michigan, R.drawable.murica_tileflipped_texas
            ));

        }

        private void initializeQuitAlertDialog(){
            Dialog alert = new Dialog(this);
            alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alert.setContentView(R.layout.alertdialog);
            alert.setCancelable(false);
            Button quitButton = alert.findViewById(R.id.alert_dialog_quit);
            quitButton.setBackground(ContextCompat.getDrawable(this,navigation_button_background));
            quitButton.setTypeface(ResourcesCompat.getFont(this,font));
            quitButton.setTextColor(ContextCompat.getColor(this,font_color));
            quitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetTimer();
                    levels.super.onBackPressed();
                }
            });
           Button dont_quit = alert.findViewById(R.id.alert_dialog_dont_quit);
           dont_quit.setBackground(ContextCompat.getDrawable(this,navigation_button_background));
           dont_quit.setTypeface(ResourcesCompat.getFont(this,font));
           dont_quit.setTextColor(ContextCompat.getColor(this,font_color));
           dont_quit.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   quitAlertDialog.cancel();
                   if (!gameFinished && gameInPlay) {
                       resumeTimer();
                   }
               }
           });
           alert.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
           quitAlertDialog = alert;
        }


        protected void loadImages(int numImages){
            images = new int[numImages];
            Random rand = new Random();
            int randomNum = 0;
            for (int i=0;i < numImages;i++){
                switch(Theme){
                    case "cartoon":
                        randomNum = rand.nextInt(cartoonImages.size());
                        images[i] = cartoonImages.get(randomNum);
                        cartoonImages.remove(randomNum);
                        continue;
                    case "murica":
                        randomNum = rand.nextInt(muricaImages.size());
                        images[i] = muricaImages.get(randomNum);
                        muricaImages.remove(randomNum);
                }
            }
        }

        protected void registersGameCards(int[] buttonIDs){
            Button btn;
            for (int i = 0;i < buttonIDs.length;i++){
                btn =(Button)findViewById(buttonIDs[i]);
                setButtonListener(btn,buttonObjects.size());
                buttonObjects.add(btn);
            }
        }

        protected void unRegisterGameCards(int[] buttonIDs){
            Button btn;
            buttonObjects = new ArrayList<>();
            for (int i = 0;i < buttonIDs.length;i++){
                btn =(Button)findViewById(buttonIDs[i]);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
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

        protected void updateBoard(ArrayList<Card> cards){
            for (int i=0;i < buttonObjects.size();i++){
                int cardNum = cards.get(i).getNumber();
                if(cardNum == -1){
                    setBackground(buttonObjects.get(i),tileBack);
                }
                else{
                    setBackground(buttonObjects.get(i),images[cardNum]);
                }
            }
            moveCounter.setText(Integer.toString(model.getMoveCount()));
        }

        private void setBackground(final Button btn, int background){
            Glide.with(this).load(ContextCompat.getDrawable(this,background)).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    btn.setBackground(resource);
                }
            });
        }

        private void swapCards(){
            //pop Up
            final View cardSwapPopUp = findViewById(R.id.cardSwapPopUp);
            cardSwapPopUp.setVisibility(View.VISIBLE);
            cardSwapPopUp.setAlpha(1f);
            cardSwapPopUp.animate().alpha(0f).setDuration(1000).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    unRegisterGameCards(buttons);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    updateBoard(model.getCheat());
                    cardSwapPopUp.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            //Card Swap
            model.shuffle();
            //updateBoard(model.getCheat());
            cardsFlippedTimer = new Timer();
            cardsFlippedTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    swapperHandler.obtainMessage(1).sendToTarget();
                }
            },cardFlipTimeUp+1000,300013221);
        }

        public Handler swapperHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                updateBoard(model.getCards());
                registersGameCards(buttons);
                cardsFlippedTimer.cancel();
                cardsFlippedTimer.purge();
            }
        };
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
            winScreen_layout.setBackground(ContextCompat.getDrawable(this,level_background));
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
            level_select.setBackground(ContextCompat.getDrawable(this,navigation_button_background));
            level_select.setTypeface(ResourcesCompat.getFont(this,font));
            level_select.setTextColor(ContextCompat.getColor(this,font_color));
            final Button next_level = (Button)findViewById(R.id.next_level_button);
            next_level.setBackground(ContextCompat.getDrawable(this,navigation_button_background));
            next_level.setTypeface(ResourcesCompat.getFont(this,font));
            next_level.setTextColor(ContextCompat.getColor(this,font_color));
            final Intent back_to_level_select = new Intent(this,level_select.class);
            level_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    levels.super.onBackPressed();
                    finish();
                }
            });
            final Intent next_level_intent = getNextLevelIntent();
            if (next_level_intent == null){
                next_level.setVisibility(View.INVISIBLE);
            }else {
                next_level.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(next_level_intent);
                        finish();
                    }
                });
            }
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
                if (hasFlip && Time_seconds != 0 && Time_seconds % flipIntervals == 0) {
                    swapCards();
                }
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
