package com.mss.instamat.model;

import java.util.ArrayList;
import java.util.List;

public class ImageListModel {

    private List<Integer> images = new ArrayList<Integer>();
    private int countClick = 0;

    public List<Integer> getImages() {
        return images;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }

    public int getCountClick() {
        return countClick;
    }

    public void setCountClick(int countClick) {
        this.countClick = countClick;
    }
}
