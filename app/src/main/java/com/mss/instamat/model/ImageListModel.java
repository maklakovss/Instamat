package com.mss.instamat.model;

import android.support.annotation.NonNull;

import com.mss.instamat.R;

import java.util.ArrayList;
import java.util.List;

public class ImageListModel {

    private static final ImageListModel instance = new ImageListModel();

    private final List<Integer> images = new ArrayList<>();
    private int countClick = 0;

    private ImageListModel() {
        for (int i = 0; i < 40; i++) {
            images.add(R.drawable.ic_android_black_24dp);
        }
    }

    @NonNull
    public static ImageListModel getInstance() {
        return instance;
    }

    @NonNull
    public List<Integer> getImages() {
        return images;
    }

    public int getCountClick() {
        return countClick;
    }

    public void setCountClick(int countClick) {
        this.countClick = countClick;
    }
}
