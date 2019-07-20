package com.mss.imagesearcher.view.imagelist;

import androidx.annotation.NonNull;

public interface IImageListViewHolder {

    int getPos();

    void setImage(@NonNull final String imageURL);

    void showProgress(boolean visible);
}
