package com.papasfritas.coronavirus.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.papasfritas.coronavirus.R;

public class MainSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_splash);
        getSupportActionBar().hide();
        initTime(5000);
    }

    private void initTime(final int Time) {

        //Luego de tiempo determinado se cambia de pantalla
        Thread timerTread = new Thread(){
            public void run(){
                try{
                    try {
                        sleep(Time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } finally {
                    // startActivity(new Intent(MainSplashActivity.this, MainActivity.class));
                    startActivity(new Intent(MainSplashActivity.this, StartActivity.class));

                }
            }
        };
        timerTread.start();
    }
}
