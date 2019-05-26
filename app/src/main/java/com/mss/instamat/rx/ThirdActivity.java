package com.mss.instamat.rx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mss.instamat.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ThirdActivity extends AppCompatActivity {

    Observer<Long> observer;
    Observable<Long> observable;
    Disposable subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        observable = Observable.interval(1, TimeUnit.SECONDS);
    }

    public void onUnsubscribeClick(View view) {
        Log.d("RX", "onUnsubscribeClick " + Thread.currentThread().getName());
        if (subscription != null) {
            subscription.dispose();
            subscription = null;
        }
    }

    public void onStartClick(View view) {
        Log.d("RX", "onStartClick " + Thread.currentThread().getName());

        observer = new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscription = d;
                Log.d("RX", "onSubscribe " + Thread.currentThread().getName());
            }

            @Override
            public void onNext(Long aLong) {
                Log.d("RX", "onNext " + aLong.toString() + " " + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                Log.e("RX", "onError " + Thread.currentThread().getName() + " " + e.toString());
            }

            @Override
            public void onComplete() {
                Log.d("RX", "onComplete " + Thread.currentThread().getName());
            }
        };

        observable
                .observeOn(Schedulers.computation())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
