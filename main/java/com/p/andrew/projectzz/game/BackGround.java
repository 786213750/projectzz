package com.p.andrew.projectzz.game;


import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BackGround {

    private Bitmap image;

    public BackGround(Bitmap bitmap,int width, int height) {
        image = Bitmap.createScaledBitmap(bitmap,width,height,false);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, 0, 0, null);
    }
}


