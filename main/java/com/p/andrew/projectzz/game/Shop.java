package com.p.andrew.projectzz.game;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.p.andrew.projectzz.R;

/**
 * Created by Andrew on 2017/9/10.
 */

public class Shop extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //turn off title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.shop);




    }
}
