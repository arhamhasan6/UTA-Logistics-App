package com.hsn.syedtaha.utalogistics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Thread td = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch (Exception ex){
                    ex.printStackTrace();
                }finally {
                    Intent intent = new Intent(SplashActivity.this, Login.class);
                    startActivity(intent);
                    finish();
                   /* SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                    if (preferences.contains("isuserlogin")) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SplashActivity.this, Login.class);
                        startActivity(intent);
                    }*/

                }
            }

        }; td.start();
    }

}