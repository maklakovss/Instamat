package com.mss.imagesearcher.presenter.main;

import androidx.annotation.NonNull;

import com.mss.imagesearcher.view.imagelist.IImageListViewHolder;

public interface IRvImageListPresenter {

    void bindView(@NonNull final IImageListViewHolder viewHolder);

    int getItemCount();

    void onImageLoaded(@NonNull final IImageListViewHolder viewHolder);

    void onImageLoadFailed(@NonNull final IImageListViewHolder viewHolder);
}