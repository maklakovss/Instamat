package com.mss.instamat.rx;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SpamGenerator implements Observable {

    List<Observer> observers = new ArrayList<>();
    String spamMessage;

    public void generateSpam(String message) {
        Log.d("SPAM", "generateSpam " + message + " " + Thread.currentThread().getName());
        spamMessage = message;
        notifyAllObservers();
    }

    @Override
    public void register(Observer observer) {
        Log.d("SPAM", "register " + Thread.currentThread().getName());
        observers.add(observer);
    }

    @Override
    public void unregister(Observer observer) {
        Log.d("SPAM", "unregister " + Thread.currentThread().getName());
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        Log.d("SPAM", "notifyAllObservers " + Thread.currentThread().getName());
        for (Observer observer : observers) {
            observer.newMessage(spamMessage);
        }
    }
}
