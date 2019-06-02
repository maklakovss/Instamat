package com.mss.instamat.presenter.imagelist;

import android.support.annotation.NonNull;

import com.mss.instamat.view.imagelist.IImageListViewHolder;

public interface IRvImageListPresenter {

    void bindView(@NonNull final IImageListViewHolder viewHolder);

    int getItemCount();

    void onImageLoaded(@NonNull final IImageListViewHolder viewHolder);

    void onImageLoadFailed(@NonNull final IImageListViewHolder viewHolder);
}
