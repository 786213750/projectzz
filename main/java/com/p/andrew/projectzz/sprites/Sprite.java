package com.p.andrew.projectzz.sprites;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;

public abstract class Sprite {

    protected float x;
    protected float y;
    protected float dx;
    protected float dy;
    protected float width;
    protected float height;
    protected float scaleX;
    protected float scaleY;
    protected Bitmap bitmap;
    protected float centerx;
    protected float centery;

    public void setcenter(){
        centerx = x + width/2;
        centery = y + height/2;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }


    public boolean intersect(Sprite sprite) {
        if (x < (sprite.getX() + sprite.getWidth())) {
            if ((sprite.getX()) < (x + width)) {
                if (y < (sprite.getY() + sprite.getHeight())) {
                    if (sprite.getY() < (y + height)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void draw(Canvas canvas) {

    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = Bitmap.createScaledBitmap(bitmap,(int)width,(int)height,false);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public float getdx() {
        return dx;
    }

    public void setdx(float dx) {
        this.dx = dx;
    }

    public float getdy() {
        return dy;
    }

    public void setdy(float dy) {
        this.dy = dy;
    }

    public float getCenterx(){
        return centerx;
    }

    public float getCentery(){
        return centery;
    }
}
