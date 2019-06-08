package com.mss.instamat.di.di;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mss.instamat.R;

public class DiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_di);
        Green green = new Green();
        Red red = new Red(green);
        White white = new White(green);
    }
}
