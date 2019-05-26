package com.mss.instamat.rx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mss.instamat.R;

import java.util.Date;

public class SecondActivity extends AppCompatActivity {

    User user = new User();
    SpamGenerator spamGenerator = new SpamGenerator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void onSubscribeClick(View view) {
        spamGenerator.register(user);
    }

    public void onUnSubscribeClick(View view) {
        spamGenerator.unregister(user);
    }

    public void onSpamClick(View view) {
        spamGenerator.generateSpam(new Date().toString());
    }
}
