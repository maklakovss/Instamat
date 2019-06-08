package com.mss.instamat.di.dagger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mss.instamat.R;

public class DaggerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);
        Red red = new Red();
        White white = new White();

    }
}
