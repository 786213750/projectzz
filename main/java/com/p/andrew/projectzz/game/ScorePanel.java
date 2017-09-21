package com.p.andrew.projectzz.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.p.andrew.projectzz.Button;
import com.p.andrew.projectzz.R;
import com.p.andrew.projectzz.sprites.Ball;


public class ScorePanel extends SurfaceView implements SurfaceHolder.Callback{

    public int score;
    private int scoreMultiplier;
    private int HighScore;
    private Button pauseButton;
    private GamePanel gamePanel;
   // private SIGame siGame;
    private MainThread thread;
    private Game game;
    //private Ball ball;
    private Canvas canvas;
    private Button button1;
    private Button button2;
    private Button button3;
    private long startTime = System.nanoTime();
    private Bitmap morepoints = BitmapFactory.decodeResource(getResources(),R.drawable.morepoints);
    private Bitmap slowDown = BitmapFactory.decodeResource(getResources(),R.drawable.slowdown);
    private Bitmap bigPad = BitmapFactory.decodeResource(getResources(),R.drawable.bigpad);

    public ScorePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setFocusable(true);
        score = 0;
        HighScore = 0;
        scoreMultiplier = 1;
    }

    public void SetThread(MainThread thread){
        this.thread = thread;
        this.thread.setScorePanel(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap pauseButton = BitmapFactory.decodeResource(getResources(), R.drawable.pausebutton);
        Bitmap pauseScaled = Bitmap.createScaledBitmap(pauseButton,getHeight(),getHeight(),false);
        this.pauseButton = new Button (getWidth()-getHeight(),0,pauseScaled);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        buttonClicked(event);
        return true;
    }

    public void buttonClicked(MotionEvent event){
        pauseButton.OnTouchEvent(event);
        if (pauseButton.getClicked()){
            gamePanel.getSiGame().pause();
        }

        if (button1 != null){
            button1.OnTouchEvent(event);
            if(button1.getClicked()) {
                PowerUpEffect(button1.getPowerUpID());
                button1 = null;
            }
        }
        if (button2 != null){
            button2.OnTouchEvent(event);
            if (button2.getClicked()) {
                PowerUpEffect(button2.getPowerUpID());
                button2 = null;
            }
        }
        if(button3 != null){
            button3.OnTouchEvent(event);
            if(button3.getClicked()) {
                PowerUpEffect(button3.getPowerUpID());
                button3 = null;
            }
        }
    }

    public void PowerUpEffect(int i){
        if (i == 1){                //more points
            if (scoreMultiplier < 16)
            scoreMultiplier = scoreMultiplier * 2;
        } else if(i == 2) {         //slowdown
            //if (gamePanel.getSiGame().getBall().getSpeed() > (4+siGame.getDifficulty()))
            gamePanel.getSiGame().getBall().speedMultiplier((float) 0.3);
        } else if(i == 3) {         //explode
            gamePanel.getSiGame().deleteSquare();
        }
    }

    @Override
    public void draw(Canvas canvas){
        this.canvas = canvas;
        if (canvas!= null) {
            final int savedState = canvas.save();

            drawBackground(canvas);
            drawScore(canvas);
            drawPowerUps();
            pauseButton.draw(canvas);
            canvas.restoreToCount(savedState);
        }
    }

    @Override
    public void onFinishInflate(){
        gamePanel = (GamePanel) findViewById(R.id.GamePanel);
    }


    public void update() {
        if (gamePanel.getSiGame().isPlaying()) {
            score = score + (1 * scoreMultiplier);

            long elapsed = (System.nanoTime() - startTime) / 1000000;
            if (elapsed > 10000) {
                startTime = System.nanoTime();
                if (scoreMultiplier > 1){
                    scoreMultiplier = scoreMultiplier / 2;
                }
            }
        }

    }

    public void drawPowerUps(){
        if (button1 != null)
            button1.draw(canvas);
        if (button2 != null)
            button2.draw(canvas);
        if (button3 != null)
            button3.draw(canvas);
    }

    public void drawScore(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize((float) (getHeight()));
        canvas.drawText(Integer.toString(score), getWidth() / 2, getHeight() - 10, paint);
        //canvas.drawText(Integer.toString(scoreMultiplier),getWidth()/3,getHeight()-10,paint);
        canvas.drawText(Float.toString( gamePanel.getSiGame().getBall().getSpeed()),getWidth()/3,getHeight()-10,paint);
        paint.setTextAlign(Paint.Align.LEFT);
        if (HighScore != 0) {
            canvas.drawText(Integer.toString(HighScore), 0, getHeight()-10, paint);
        }
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    private void drawBackground(Canvas canvas){
        Bitmap bitbg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.bg1),getWidth(),getHeight(),false);
        BackGround b = new BackGround(bitbg,canvas.getWidth(),canvas.getHeight());
        b.draw(canvas);
    }

    public void setHighScore(){
        if(score > HighScore) {
            HighScore = score;
            game.saveScore(HighScore);
        }
        score = 0;
    }

    public void setActivity(Game game){
        this.game = game;
    }

    public void setHighScore(int hs){
        HighScore = hs;
    }

    public int getHighScore(){
        return HighScore;
    }

    public int getScore() {
        return score;
    }

    public void getPowerUp(int s){
        if (s == 1){
            Button pu = new Button(0,0, scaledButton(morepoints));
            pu.setPowerUpID(s);
            nextPUButton(pu);
        } else if (s == 2){
            Button pu = new Button(0,0, scaledButton(slowDown));
            pu.setPowerUpID(s);
            nextPUButton(pu);
        } else if (s == 3){
            Button pu = new Button(0,0, scaledButton(bigPad));
            pu.setPowerUpID(s);
            nextPUButton(pu);
        }
    }

    public Bitmap scaledButton(Bitmap bitmap){
        return Bitmap.createScaledBitmap(bitmap,getHeight(),getHeight(),false);
    }

    public void nextPUButton(Button powerUp){
        int spaceing = getHeight();
        int x = getWidth()*3/4 - spaceing*3/2;
        if (button1 == null){
            powerUp.setX(x);
            button1 = powerUp;

        } else if (button2 == null){
            powerUp.setX(x + spaceing);
            button2 = powerUp;

        } else if (button3 == null){
            powerUp.setX(x + spaceing*2);
            button3 = powerUp;

        } else {

        }
    }

    public int getScoreMultiplier() {
        return scoreMultiplier;
    }

    public void setScoreMultiplier(int scoreMultiplier){
        this.scoreMultiplier = scoreMultiplier;
    }
}
