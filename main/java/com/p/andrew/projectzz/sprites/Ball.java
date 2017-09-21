package com.p.andrew.projectzz.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.p.andrew.projectzz.game.SIGame;


public class Ball extends Sprite {

    private float speed;
    private float angle;
    private float dangle;
    private float baseSpeed;
    private SIGame siGame;

    public Ball(Bitmap bitmap,SIGame siGame) {
        width = 25;
        height = 25;
        setBitmap(bitmap);
        restart();
        this.siGame = siGame;
    }

    public void restart(){
        speed = (float) 4;
        angle = 0;
        dangle=0;
        x = ((SIGame.WIDTH / 2) - (width / 2));
        y = ((SIGame.HEIGHT / 2) - (height / 2));
    }

    public void setAngle(float angle){
        this.angle = angle;
    }

    public float getAngle(){
        return angle;
    }

    public void update () {
        //angle += dangle;
        if(speed< 4+siGame.getDifficulty()){
            speed = 4+siGame.getDifficulty();
        }
        if (angle > 360)
            angle -= 360;
        dx = (float) (speed * Math.cos(angle * Math.PI/180));
        dy = (float) (speed * Math.sin(angle * Math.PI/180));
        x += dx;
        y += dy;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap,x,y,null);
    }

    public void speedMultiplier(float multiplier) {
        this.speed = this.speed * multiplier;
    }

    public float getSpeed() {
        return speed;
    }

    public float getDangle(){ return angle; }

    public void setDangle(float dangle){this.dangle = dangle;}

    public void setSpeed(float speed){ this.speed = speed; }
}
