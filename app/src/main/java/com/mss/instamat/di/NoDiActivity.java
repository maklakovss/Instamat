package com.mss.instamat.di;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mss.instamat.R;

public class NoDiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_di);
        Red red = new Red();
        White white = new White();
    }
}
