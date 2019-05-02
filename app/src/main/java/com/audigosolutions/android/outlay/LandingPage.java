package com.audigosolutions.android.outlay;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class LandingPage extends AppCompatActivity {


    private ActionBar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        toolBar = getSupportActionBar();

        Fragment fragment;
        fragment = new AllCards();
        loadFragent(fragment);

        BottomNavigationView nav = (BottomNavigationView)findViewById(R.id.bottomnavbar);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment;

                switch (item.getItemId())
                {
                    case R.id.availiblecards:
                        toolBar.setTitle("Availible Cards");
                        fragment = new AllCards();
                        loadFragent(fragment);
                        return true;

                    case R.id.addcard:
                        toolBar.setTitle("Add Card");
                        fragment = new AddCard();
                        loadFragent(fragment);
                        return true;

//                    case R.id.profile:
//                        toolBar.setTitle("Profile");
//                        fragment = new Profile();
//                        loadFragent(fragment);
//                        return true;
                }
                return false;
            }
        });
    }

    public void loadFragent(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
