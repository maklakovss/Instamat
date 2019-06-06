package com.mss.instamat.view.imagelist;

public interface IImageListViewHolder {

    int getPos();

    void setImage(String imageURL);

    void showProgress(boolean visible);
}
