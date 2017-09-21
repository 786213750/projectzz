package com.p.andrew.projectzz.game;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.p.andrew.projectzz.Button;
import com.p.andrew.projectzz.R;

/**
 * Created by thomas on 2017/9/10.
 */

public class ShopPanel extends SurfaceView implements SurfaceHolder.Callback{
    private Button backButton;
    private BackGround bg;
    private MainThread mainThread;



    public ShopPanel(Context context, AttributeSet attrs) {
        super(context,attrs);
        mainThread= new MainThread(this);
        getHolder().addCallback(this);
        setFocusable(true);
        bg = new BackGround(BitmapFactory.decodeResource(getResources(), R.drawable.greenbg),1840,960);
        mainThread.setRunning(true);
        mainThread.setShopPanel(this);
        if(mainThread.getState() != Thread.State.TERMINATED) {
            mainThread.start();
        }

    }
    @Override
    public void draw(Canvas canvas){
        final int savedState = canvas.save();
        bg.draw(canvas);
        canvas.restoreToCount(savedState);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
