package com.audigosolutions.android.outlay;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    private TextView head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        head = findViewById(R.id.headTitle);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/pacific-font.ttf");
        head.setTypeface(typeface);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                final String PREFS_NAME = "MyPrefsFile";

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

                if (settings.getBoolean("my_first_time", true)) {
                    //the app is being launched for first time, do something
                    Log.d("Comments", "First time");

                    Intent i = new Intent(SplashScreen.this, UserRegistration.class);
                    startActivity(i);

                    settings.edit().putBoolean("my_first_time", false).apply();
                }
                else
                {
                    Intent i = new Intent(SplashScreen.this, LogIn.class);
                    startActivity(i);
                }

            }
        },2000);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {


        if (doubleBackToExitPressedOnce) {
            finishAffinity();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }
}
