package com.mss.instamat.lambda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mss.instamat.R;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class First1Activity extends AppCompatActivity {

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first1);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnRegister)
    public void onRegisterClick(View view) {
        disposable = Observable.create(e -> {
            for (int i = 0; i < 100; i++) {
                e.onNext(i);
                TimeUnit.SECONDS.sleep(1);
                Log.d("Observable", Thread.currentThread().getName());
            }
            e.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> Log.d("subscribe", Thread.currentThread().getName()));
    }

    @OnClick(R.id.btnUnregister)
    public void onUnregisterClick(View view) {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }
}
