package com.cvetici.beeorganised;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class LoadingScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Animation top, bottom;
        ImageView topi, bottomi;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
       top = AnimationUtils.loadAnimation(this, R.anim.loading_top);
        bottom = AnimationUtils.loadAnimation(this, R.anim.loading_bottom);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        topi = findViewById(R.id.gore);
        bottomi = findViewById(R.id.dole);
        topi.setAnimation(top);
        bottomi.setAnimation(bottom);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(LoadingScreen.this, WorkerActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}