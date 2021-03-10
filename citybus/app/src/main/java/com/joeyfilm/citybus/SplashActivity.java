package com.joeyfilm.citybus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends  AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler3 = new Handler();
       handler3.postDelayed(new Runnable() {
            @Override
            public void run() {

            Intent intent = new Intent(getApplicationContext(),LoginMain.class);
            startActivity(intent);
                overridePendingTransition(0, 0);

            }
        },2000);

    }

}