package com.dev_bourheem.hadi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(splash.this)
                .withFullScreen()
                .withTargetActivity(Main3Activity.class)
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("#64DEC5"))
               // .withBackgroundResource(R.drawable.lkarnetp)
                .withFooterText("Copyright 2020 , Dev-Bourheem ")
                .withAfterLogoText(" حانوتي - لكارني")
                .withLogo(R.mipmap.ic_launcher);

        config.getFooterTextView().setTextColor(Color.WHITE);
        config.getFooterTextView().setTextSize(12);
        config.getAfterLogoTextView().setTextSize(36);
        config.getAfterLogoTextView().setTextColor(Color.WHITE);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}
