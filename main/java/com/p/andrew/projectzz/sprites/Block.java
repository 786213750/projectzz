package com.p.andrew.projectzz.sprites;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.p.andrew.projectzz.Grid;

public class Block extends Sprite {

    private Detector detectorU;
    private Detector detectorD;
    private Detector detectorL;
    private Detector detectorR;
    private Detector detectorUR;
    private Detector detectorDR;
    private Detector detectorDL;
    private Detector detectorUL;
    private int dirc;
    private int hitPoint;
    private Paint text = new Paint();

    public Block(Bitmap bitmap,float x,float y, Grid grid,int hitPoint){
        width = grid.getxSize();
        height = grid.getySize();
        setBitmap(bitmap);
        this.x = x;
        this.y = y;
        detectorU = new Detector(x,y-height,width,height);
        detectorD = new Detector(x,y+height,width,height);
        detectorL = new Detector(x-width,y,width,height);
        detectorR = new Detector(x+width,y,width,height);
        detectorUR = new Detector(x+width,y-height,width,height);
        detectorDR = new Detector(x+width,y+height,width,height);
        detectorDL = new Detector(x-width,y+height,width,height);
        detectorUL = new Detector(x-width,y-height,width,height);
        setcenter();
        this.hitPoint = hitPoint;
        text.setTextSize(height);
        text.setColor(Color.BLACK);
        text.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap,x,y,null);
        canvas.drawText(Integer.toString(hitPoint),centerx,centery + 17,text);
    }

    public void update(Ball ball) {
        if(ball.intersect(detectorU) || ball.intersect(detectorD)){
            dirc = 1;
        }  else if (ball.intersect(detectorL) || ball.intersect(detectorR)){
            dirc = 2;
        }  else if (ball.intersect(detectorUR) || ball.intersect(detectorDR) || ball.intersect(detectorDL) || ball.intersect(detectorUL)){
            dirc = 0;
        }
    }

    public int getDirc() {
        return dirc;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public void hit(int i){
        hitPoint = hitPoint - i;
    }
}
