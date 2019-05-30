package com.mss.instamat.presenter;

import android.support.annotation.NonNull;

import com.mss.instamat.view.imagelist.IImageListViewHolder;

public interface IRvImageListPresenter {

    void bindView(@NonNull IImageListViewHolder viewHolder);

    int getItemCount();
}
