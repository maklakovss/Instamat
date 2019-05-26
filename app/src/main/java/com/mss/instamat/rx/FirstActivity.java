package com.mss.instamat.rx;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mss.instamat.R;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    public void onClickStartButton(View view) {
        MyTask myTask = new MyTask();
        myTask.execute();
        Log.d("RX", "Click ended " + Thread.currentThread().getName());
    }

    private static class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("RX", "onPreExecute " + Thread.currentThread().getName());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("RX", "onPostExecute " + Thread.currentThread().getName());

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                for (int i = 0; i < 5; i++) {
                    Log.d("RX", "doInBackground " + Thread.currentThread().getName() + " - " + i);
                    Thread.sleep(1000);
                }
                Log.d("RX", "doInBackground " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
