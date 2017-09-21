package com.p.andrew.projectzz.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.p.andrew.projectzz.R;


public class Game extends Activity {

    private MainThread thread;
    private ScorePanel scorePanel;
    private GamePanel gamePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thread = new MainThread();


        //turn off title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.game);

        scorePanel = (ScorePanel) findViewById(R.id.ScorePanel);
        scorePanel.SetThread(thread);
        scorePanel.setActivity(this);
        SharedPreferences pref = this.getSharedPreferences("highScore",Context.MODE_PRIVATE);
        scorePanel.setHighScore(pref.getInt("highScore",0));

        gamePanel = (GamePanel) findViewById(R.id.GamePanel);
        gamePanel.setActivity(this);
        gamePanel.SetThread(thread);
    }

    public void saveScore(int score){
        SharedPreferences pref = getSharedPreferences("highScore", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("highScore",score);
        editor.commit();
    }



    @Override
    protected void onRestart(){
        super.onRestart();
        thread = new MainThread(scorePanel,gamePanel);
    }


    @Override
    public void onBackPressed(){
        gamePanel.getSiGame().pause();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("HighScore",scorePanel.getHighScore());
    }

    public void restart(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recreate();
            }
        });
    }
}
