package com.p.andrew.projectzz.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.p.andrew.projectzz.R;
import com.p.andrew.projectzz.game.Game;


public class TitleScreen extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.titlescreen);
    }

    public void onStart(View v) {                           //startbutton
        if(v.getId() == R.id.startbutton){
            Intent i = new Intent(this, Game.class);
            startActivity(i);
        }
    }

    public void onshop(View v) {
        if (v.getId() == R.id.shopbutton) {
            Intent e = new Intent(this, Shop.class);
            startActivity(e);
        }
    }





}
