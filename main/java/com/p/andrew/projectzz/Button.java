package com.p.andrew.projectzz;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;


public class Button {

    private float x;
    private float y;
    private float Width;
    private float Height;
    private Bitmap bitmap;
    private Boolean clicked;
    private Paint paint;
    private String text;
    private int id;             //1- more points
                                //2- slowdown
                                //3- big pad

    public Button (float x,float y, Bitmap pressed){
        this.x = x;
        this.y = y;
        this.Width = pressed.getWidth();
        this.Height = pressed.getHeight();
        this.bitmap = pressed;
        clicked = false;
    }

    public Button (float x, float y, Paint paint,String text,float width,float height){
        this.x = x;
        this.y = y;
        this.Width = width;
        this.Height = height;
        this.paint = paint;
        this.text = text;
        clicked = false;
    }

    public void OnTouchEvent(MotionEvent event){
        if        ( event.getX() > x
                &&  event.getX() < x + Width
                &&  event.getY() > y
                &&  event.getY() < y + Height) {
            if (event.getAction() == event.ACTION_DOWN){
                clicked = true;
            }
            if (event.getAction() == event.ACTION_UP){
                clicked = false;
            }
        }
    }

    public void draw(Canvas canvas) {
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, x, y, null);
        }else{
            canvas.drawLine(x,y,x+Width,y,paint);
            canvas.drawLine(x+Width,y,x+Width,y+Height,paint);
            canvas.drawLine(x,y+Height,x,y,paint);
            canvas.drawLine(x,y+Height,x+Width,y+Height,paint);
            canvas.drawText(text,x + Width/2,y+Height,paint);
        }
    }

    public boolean getClicked(){
        return clicked;
    }

    public void setClicked(Boolean b){
        clicked = b;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public void setPowerUpID(int i){
        this.id = i;
    }

    public int getPowerUpID(){
        return id;
    }
}
