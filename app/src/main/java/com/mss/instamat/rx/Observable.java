package com.mss.instamat.rx;

public interface Observable {
    void register(Observer observer);

    void unregister(Observer observer);

    void notifyAllObservers();
}
