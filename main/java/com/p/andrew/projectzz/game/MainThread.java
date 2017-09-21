package com.p.andrew.projectzz.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;


public class MainThread extends Thread{

    private int FPS = 60;
    private double averageFPS;
    private static SurfaceHolder surfaceHolder;
    private static SurfaceHolder surfaceHoldersp;
    private GamePanel gamePanel;
    private ScorePanel scorePanel;
    private ShopPanel shopPanel;
    private boolean running;
    public static Canvas canvas;
    private static SurfaceHolder surfaceHoldershop;

    public MainThread (){
        super();
    }

    public MainThread (ScorePanel sp,GamePanel gp){
        scorePanel = sp;
        scorePanel.SetThread(this);
        gamePanel = gp;
        gamePanel.SetThread(this);

    }
    public MainThread (ShopPanel shopPanel){
        this.shopPanel = shopPanel;
    }

    public void setGamePanel(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        this.surfaceHolder = gamePanel.getHolder();
    }

    public void setScorePanel(ScorePanel scorePanel){
        this.scorePanel = scorePanel;
        this.surfaceHoldersp = scorePanel.getHolder();
    }

    public void setShopPanel(ShopPanel shopPanel){
        this.shopPanel = shopPanel;
        this.surfaceHoldershop = shopPanel.getHolder();
    }

    @Override
    public void run(){
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int framecount = 0;
        long targetTime = 1000/FPS;

        while(running){
            startTime = System.nanoTime();


            canvas = null;
            if(shopPanel!=null) {
                try {
                    canvas = this.surfaceHoldershop.lockCanvas();
                    synchronized (surfaceHoldershop) {
                        //this.shopPanel.update();
                        this.shopPanel.draw(canvas);
                    }
                } catch (Exception e) {

                } finally {
                    if (canvas != null) {
                        try {
                            surfaceHoldershop.unlockCanvasAndPost(canvas);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            canvas = null;
            if(scorePanel!=null) {
                try {
                    canvas = this.surfaceHoldersp.lockCanvas();
                    synchronized (surfaceHoldersp) {

                        scorePanel.setGamePanel(gamePanel);
                        this.scorePanel.update();
                        this.scorePanel.draw(canvas);
                    }
                } catch (Exception e) {

                } finally {
                    if (canvas != null) {
                        try {
                            surfaceHoldersp.unlockCanvasAndPost(canvas);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            canvas = null;

            //locking canvas for pixel editing
            if(gamePanel!=null) {
                try {
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {

                        gamePanel.setScorePanel(scorePanel);
                        this.gamePanel.update();
                        this.gamePanel.draw(canvas);
                    }
                } catch (Exception e) {
                    Exception ew = e;
                } finally {
                    if (canvas != null) {
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime-timeMillis;

            try{
                this.sleep(waitTime);
            }catch(Exception e){}


            totalTime += System.nanoTime() - startTime;
            framecount++;
            if(framecount == FPS){
                averageFPS = 1000/((totalTime/framecount) / 1000000);
                framecount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }

    public void setRunning(boolean b){
        running = b;
    }

    public ScorePanel getScorePanel(){
        return scorePanel;
    }

    public GamePanel getGamePanel(){
        return gamePanel;
    }
}
