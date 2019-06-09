package com.mss.instamat.view.imagelist;

import android.support.annotation.NonNull;

public interface IImageListViewHolder {

    int getPos();

    void setImage(@NonNull final String imageURL);

    void showProgress(boolean visible);
}
