package com.timboat.coolapp;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class SplashScreen extends AppCompatActivity {

    private GifImageView gif;
    private ImageView lastFrame,startFrame,slogan;
    private Animation fade_out,fade_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        gif = (GifImageView) findViewById(R.id.gif);
        slogan = (ImageView) findViewById(R.id.slogan_id);
        lastFrame = (ImageView) findViewById(R.id.last_frame);
        fade_out = AnimationUtils.loadAnimation(this,R.anim.splash_fade_out);
        fade_in = AnimationUtils.loadAnimation(this,R.anim.fade_in_activity);
        startFrame = (ImageView) findViewById(R.id.start_frame);
        startFrame.startAnimation(fade_in);
        slogan.startAnimation(fade_in);
        startFrame.setVisibility(View.VISIBLE);
        gif.startAnimation(fade_in);

        try{
            InputStream inputStream = getAssets().open("logo.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gif.setBytes(bytes);
            gif.startAnimation();
        }
        catch (IOException ex)
        {
        }

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                startFrame.setVisibility(View.INVISIBLE);
                lastFrame.setVisibility(View.VISIBLE);
                gif.setVisibility(View.INVISIBLE);
                lastFrame.setVisibility(View.INVISIBLE);
                lastFrame.startAnimation(fade_out);
                slogan.startAnimation(fade_out);

                SplashScreen.this.startActivity
                        (new Intent(SplashScreen.this,Login.class));
                overridePendingTransition(0,0);
                SplashScreen.this.finish();
            }
        },2700);
    }
}
