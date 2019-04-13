package com.mvhoang.welcome.baitapcuoiki;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeMiniGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_mini_game);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MenuHome fragment = new MenuHome();
        transaction.add(R.id.mylayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
