package com.p.andrew.projectzz.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.p.andrew.projectzz.game.SIGame;

public class Player extends Sprite {

    private int player;
    private int offset;
    private Detector detectorU;
    private Detector detectorD;
    private Detector detectorL;
    private Detector detectorR;
    private int dirc;

    public Player(Bitmap res,int p,SIGame game) {
        height = 120;
        width = 24;
        offset = (int) (game.WIDTH/10);
        setBitmap(res);
        dirc = 2;
        if (p == 1) {                           //player1 (right)
            player = p;
            startY();
            x = SIGame.WIDTH - offset;
        } else {                                //player2 (left)
            player = p;
            startY();
            x = offset;
        }
    }

    public void startY(){
        y = (SIGame.HEIGHT / 2) - (height / 2);
    }

    public void setUp (float up) {
        if(up > 0) {
            y = up - (height / 2);
        }
    }

    public void update(Ball ball) {
        detectorU = new Detector(x, y - height, width, width);
        detectorD = new Detector(x, y + height, width, width);
        detectorL = new Detector(x - width, y, width, height);
        detectorR = new Detector(x + width, y, width, height);
        if (ball.intersect(detectorR)) {
            dirc = 3;
        } else if (ball.intersect(detectorL)) {
            dirc = 2;
        } else if (ball.intersect(detectorU) || ball.intersect(detectorD)) {
            dirc = 1;
        }
    }

    public int getDirc(){
        return dirc;
    }

    public int getOffset(){
        return offset;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap,x,y,null);
    }

    public void setDirc(int dirc) {
        this.dirc = dirc;
    }

    public int getplayer() {
        return player;
    }

    public void scaleSize(float s) {
        height = height * s;
    }
}
