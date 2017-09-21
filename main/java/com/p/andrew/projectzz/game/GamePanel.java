package com.p.andrew.projectzz.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private SIGame siGame;
    private Game game;
    private float scaleFactorX;
    private float scaleFactorY;
    private ScorePanel scorePanel;
    private MainThread thread;


    public GamePanel(Context context, AttributeSet attrs) {
        super(context,attrs);
        getHolder().addCallback(this);
        setFocusable(true);
    }

    public void SetThread(MainThread thread){
        this.thread = thread;
        this.thread.setGamePanel(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (siGame == null) {
            siGame = new SIGame(getResources());
        }
        thread.setRunning(true);
        if(thread.getState() != Thread.State.TERMINATED) {
            thread.start();
        }
        siGame.setPause();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float y1 = -1;
        float y2 = -1;

        if (siGame.getend()) {
            event.setLocation(refactorX((int) event.getX()), refactorY((int) event.getY()));
            siGame.getRestartButton().OnTouchEvent(event);
            siGame.getBackButton().OnTouchEvent(event);
            if (siGame.getRestartButton().getClicked()){
                getGame().restart();
            }
            if (siGame.getBackButton().getClicked()) {
                ((Activity) getContext()).finish();
            }
        }
            if (!siGame.getPause()) {
                for(int i = 0;i < event.getPointerCount();i++) {
                    int x = refactorX((int) event.getX(i));
                    int y = refactorY((int) event.getY(i));
                    if (x > 2 * siGame.WIDTH / 3) {
                        if (y > siGame.HEIGHT - siGame.getPlayer1().getHeight()/2){
                            y1 = siGame.HEIGHT - siGame.getPlayer1().getHeight()/2;
                        }else if (y < siGame.getPlayer1().getHeight()/2){
                            y1 = siGame.getPlayer1().getHeight()/2;
                        }else {
                            y1 = y;
                        }
                    }
                    if (x < siGame.WIDTH / 3) {
                        if (y > siGame.HEIGHT - siGame.getPlayer2().getHeight()/2){
                            y2 = siGame.HEIGHT - siGame.getPlayer2().getHeight()/2;
                        }else if (y < siGame.getPlayer2().getHeight()/2){
                            y2 = siGame.getPlayer2().getHeight()/2;
                        }else {
                            y2 = y;
                        }
                    }
                }
                    if (y1 != -1) {
                        siGame.getPlayer1().setUp(y1);
                    }
                    if (y2 != -1) {
                        siGame.getPlayer2().setUp(y2);
                    }


                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (siGame.getStart()) {
                        siGame.start();
                    }
                    return true;
                }
                return super.onTouchEvent(event);
            } else {
                event.setLocation(refactorX((int) event.getX()), refactorY((int) event.getY()));
                siGame.getBackButton().OnTouchEvent(event);
                siGame.getResumeButton().OnTouchEvent(event);
                if (siGame.getResumeButton().getClicked()) {
                    siGame.unPause();
                    siGame.getResumeButton().setClicked(false);
                }
                if (siGame.getBackButton().getClicked()) {
                    ((Activity) getContext()).finish();

                }
            }
        return true;
    }

    @Override
    public void draw(Canvas canvas){
        if (canvas!= null){
            scaleFactorX = (float) getWidth()/ siGame.WIDTH;
            scaleFactorY = (float) getHeight()/ siGame.HEIGHT;
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX,scaleFactorY);
            siGame.draw(canvas);
            canvas.restoreToCount(savedState);
        }
    }

    public void update() {
        siGame.update();
        siGame.setScorePanel(scorePanel);
        siGame.setGamePanel(this);
    }

    public void setScorePanel(ScorePanel scorePanel) {
        this.scorePanel = scorePanel;
    }

    public SIGame getSiGame(){
        return siGame;
    }

    public int refactorX (int x){
        return (int) (x / scaleFactorX);
    }

    public int refactorY(int y){
        return (int) (y / scaleFactorY);
    }

    public void setActivity(Game game) {
        this.game = game;
    }

    public Game getGame(){
        return game;
    }
}
