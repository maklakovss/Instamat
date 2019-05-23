package com.mss.instamat.ui.view;

import com.arellomobile.mvp.MvpView;

import java.util.List;

public interface ImageListView extends MvpView {

    void initImageList(List<Integer> images);
}
