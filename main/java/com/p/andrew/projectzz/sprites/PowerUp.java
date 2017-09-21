package com.p.andrew.projectzz.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.p.andrew.projectzz.Grid;


public class PowerUp extends Sprite{

    public PowerUp (Bitmap bitmap, float x, float y, Grid grid){
        width = grid.getxSize();
        height = grid.getySize();
        this.x = x;
        this.y = y;
        setBitmap(bitmap);
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap,x,y,null);
    }
}
