package com.mss.instamat.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mss.instamat.model.ImageListModel;
import com.mss.instamat.model.ImagesResponse;
import com.mss.instamat.view.imagelist.IImageListViewHolder;
import com.mss.instamat.view.imagelist.ImageListView;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class ImageListPresenter extends MvpPresenter<ImageListView> {

    private final ImageListModel model = ImageListModel.getInstance();
    private final RvPresenter rvPresenter = new RvPresenter();

    public void onItemClick(int position) {
        getViewState().openDetailActivity(position);
    }

    @NonNull
    public RvPresenter getRvPresenter() {
        return rvPresenter;
    }

    public void onSearchClick(String searchText) {
        getViewState().showProgress(true);
        model.findImages(searchText)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imagesResponse -> doOnSuccess(imagesResponse),
                        throwable -> doOnError(throwable));
    }

    private void doOnError(Throwable throwable) {
        getViewState().showProgress(false);
        Log.d("", throwable.toString());
    }

    private void doOnSuccess(ImagesResponse imagesResponse) {
        getViewState().showProgress(false);
        getViewState().initImageList();
    }


    class RvPresenter implements IRvImageListPresenter {

        @Override
        public void bindView(@NonNull IImageListViewHolder viewHolder) {
            viewHolder.setImage(model.getImagesResponse().getHits().get(viewHolder.getPos()).getPreviewURL());
        }

        @Override
        public int getItemCount() {
            return model.getImagesResponse().getHits().size();
        }

        @Override
        public void onImageLoaded(@NonNull IImageListViewHolder viewHolder) {
            viewHolder.showProgress(false);
        }
    }
}
