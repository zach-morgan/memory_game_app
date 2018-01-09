package com.example.zach.memorygame;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Zach on 1/8/2018.
 */

public abstract class baseLevel extends AppCompatActivity implements Observer {

    private ConcentrationModel model;

    protected LinearLayout mainLayout;

    private View game_layout;

    private View winScreen_layout;

    private TextView TimeView;

    private TextView levelLabel;

    private Button resetButton;

    private View level_intro;

    private static int[] images;

    private static ArrayList<Integer> cartoonImages;

    private static int[] buttons;

    private static ArrayList<Button> buttonObjects;

    private TextView moveCounter;

    private static Timer timer;

    private static int Time_minutes;

    private static int Time_seconds;

    private static boolean isTimerGoing;

    private AlertDialog quitAlertDialog;

    private boolean gameFinished = false;

    private boolean gameInPlay = false;

    private String[] goals;

    private ViewGroup.LayoutParams layoutparams;

    private TextView Move;

    private String levelnumber;

    public baseLevel(){
        model = new ConcentrationModel();
        model.addObserver(this);
        initializeGlobalHeader();
        initializeImageArrays();

    }

    private void initializeGlobalHeader(){
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        moveCounter = findViewById(R.id.move_counter);
        TimeView = findViewById(R.id.timer);
        resetButton = findViewById(R.id.reset_button);
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
    }

    private void initializeLevelIntroduction(String[] params) {
        level_intro = getLayoutInflater().inflate(R.layout.level_introduction, mainLayout, false);
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
        Button btn = (Button) findViewById(R.id.intro_playButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(mainLayout);
                layoutparams = level_intro.getLayoutParams();
                layoutparams.height = 0;
                level_intro.setLayoutParams(layoutparams);
                mainLayout.addView(game_layout);
                animateHeader();
                playStage1Level1();
            }
        });
    }

    private void initializeImageArrays(){
        buttonObjects = new ArrayList<>();
        cartoonImages = new ArrayList<>(Arrays.asList(
                R.drawable.cartoon_tileback_whale,R.drawable.cartoon_tileflipped_cloud,R.drawable.cartoon_tileflipped_cuteprince,R.drawable.cartoon_tileflipped_punkguy
                ,R.drawable.cartoon_elephant
        ));
    }


    private void loadImages(int numImages, String theme) {
        images = new int[numImages];
        Random rand = new Random();
        for (int i = 0; i < numImages; i++) {
            switch (theme) {
                case "Cartoon":
                    int randomNum = rand.nextInt(cartoonImages.size());
                    images[i] = cartoonImages.get(randomNum);
                    cartoonImages.remove(randomNum);
            }
        }
    }

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
        View victory_header = findViewById(R.id.victory_header);
        TextView Move = findViewById(R.id.move_label);
        Move.animate().alpha(0f).setDuration(1000).setListener(null);
        TimeView.animate().alpha(0f).setDuration(1000).setListener(null);
        levelLabel.animate().alpha(0f).setDuration(1000).setListener(null);
        resetButton.animate().alpha(0f).setDuration(1000).setListener(null);
        moveCounter.animate().alpha(0f).setDuration(1000).setListener(null);
        victory_header.animate().alpha(1f).setDuration(1000).setListener(null);
        winScreen_layout = getLayoutInflater().inflate(R.layout.trophy_screen,mainLayout,false);
        TransitionManager.beginDelayedTransition(mainLayout);
        layoutparams = game_layout.getLayoutParams();
        layoutparams.height = 0;
        game_layout.setLayoutParams(layoutparams);
        mainLayout.addView(winScreen_layout);
        String medal = level_of_win();
        ImageView gold_trophy = findViewById(R.id.gold_trophy_win);
        ImageView silver_trophy = findViewById(R.id.silver_trophy_win);
        ImageView bronze_trophy = findViewById(R.id.bronze_trophy_win);

        switch(medal){
            case "gold":
                gold_trophy.setVisibility(View.VISIBLE);
                //animateTrophy(gold_trophy);
                break;
            case "silver":
                silver_trophy.setVisibility(View.VISIBLE);
                break;
            //animateTrophy(silver_trophy);
            case "bronze":
                bronze_trophy.setVisibility(View.VISIBLE);
                break;
            //animateTrophy(bronze_trophy);
            case "no_medal":

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

    private void animateHeader(){
        TextView Move = findViewById(R.id.move_label);
        Move.animate().alpha(1f).setDuration(1500).setListener(null);
        TimeView.animate().alpha(1f).setDuration(1500).setListener(null);
        levelLabel.animate().alpha(1f).setDuration(1500).setListener(null);
        resetButton.animate().alpha(1f).setDuration(1500).setListener(null);
        moveCounter.animate().alpha(1f).setDuration(1500).setListener(null);
    }

    private void registersButtons(int[] buttonIDs){
        Button btn;
        for (int i = 0;i < buttonIDs.length;i++){
            btn =(Button)findViewById(buttonIDs[i]);
            setButtonListener(btn,i);
            buttonObjects.add(btn);
        }
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.reset();
                resetTimer();
            }
        });
    }

    private void setButtonListener(Button btn,final int index){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.selectCard(index);
            }
        });
    }

    private void updateBoard(ArrayList<Card> cards){
        for (int i=0;i < buttons.length;i++){
            int cardNum = cards.get(i).getNumber();
            if(cardNum == -1){
                buttonObjects.get(i).setBackgroundResource(R.drawable.cartoon_tileback);
            }
            else{
                buttonObjects.get(i).setBackgroundResource(images[cardNum]);
            }
        }
        moveCounter.setText(Integer.toString(model.getMoveCount()));
    }

    //MAKE A STOP TIMER
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


}
