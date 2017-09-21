package com.p.andrew.projectzz.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.p.andrew.projectzz.Grid;
import com.p.andrew.projectzz.sprites.Block;

public class Portal extends Sprite {

    private Portal end;

    public Portal(Bitmap bitmap, float x, float y, Grid grid) {
        width = grid.getxSize();
        height = grid.getySize();
        setBitmap(bitmap);
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap,x,y,null);
    }

    public void setEnd(Portal portal){
        this.end = portal;
        if(portal.getEnd() != this) {
            portal.setEnd(this);
        }
    }

    public Portal getEnd(){
        return end;
    }
}
