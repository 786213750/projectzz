package com.p.andrew.projectzz.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.p.andrew.projectzz.Grid;


public class Debuff extends Sprite {

    private int id;                 //0 = slowdown
                                    //1 = arc
                                    //2 = random angle
                                    //3 = no points

    public Debuff (Bitmap bitmap, float x, float y, Grid grid, int id){
        this.width = grid.getxSize();
        this.height = grid.getySize();
        setBitmap(bitmap);
        this.x = x;
        this.y = y;
        this.id = id;
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap,x,y,null);
    }

    public int getId() {
        return id;
    }
}
