package com.example.rizki.JakarteKite.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rizki.JakarteKite.R;

public class SplashScreenActivity extends Activity {

    ImageView imageViewSplash;
    TextView tvAppName;
    RelativeLayout relative;
    Thread SplashThead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageViewSplash = (ImageView) findViewById(R.id.imageView2);
        tvAppName = (TextView) findViewById(R.id.tvAppName);
        relative = (RelativeLayout) findViewById(R.id.relative);

        startAnimation();
    }

    private void startAnimation(){
        Animation rotate = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.rotate);
        Animation translate = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.translasi);

        rotate.reset();
        translate.reset();
        relative.clearAnimation();

        imageViewSplash.startAnimation(rotate);
        tvAppName.startAnimation(translate);
        SplashThead = new Thread(){
            @Override
            public void run(){
                super.run();
                int waited = 0;
                while(waited < 4000){
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    waited += 100;
                }
                SplashScreenActivity.this.finish();
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
            }
        };
        SplashThead.start();
    }
}
