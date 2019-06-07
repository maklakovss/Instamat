package com.mss.instamat.presenter.imagelist;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mss.instamat.model.ImageListModel;
import com.mss.instamat.model.models.ImagesResponse;
import com.mss.instamat.view.imagelist.IImageListViewHolder;
import com.mss.instamat.view.imagelist.ImageListView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class ImageListPresenter extends MvpPresenter<ImageListView> {

    private final ImageListModel model = ImageListModel.getInstance();
    private final RvPresenter rvPresenter = new RvPresenter();
    private String lastQuery = "";
    private int lastPage = 0;
    private Disposable lastDisposableQuery = null;
    private boolean end = false;

    public void onItemClick(int position) {
        getViewState().openDetailActivity(position);
    }

    @NonNull
    public RvPresenter getRvPresenter() {
        return rvPresenter;
    }

    public void onNeedNextPage(String searchText) {
        if (!searchText.equals(lastQuery)) {
            if (lastDisposableQuery != null) {
                lastDisposableQuery.dispose();
                lastDisposableQuery = null;
            }
            lastQuery = searchText;
            lastPage = 0;
            end = false;
            model.clearImages();
            getViewState().refreshImageList();
        }
        if (lastDisposableQuery == null) {
            getViewState().showProgress(true);
            lastDisposableQuery = model.getImagesFromNetwork(searchText, ++lastPage)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(imagesResponse -> doOnSuccess(imagesResponse),
                            throwable -> doOnError(throwable));
        }
    }

    private void doOnError(Throwable throwable) {
        getViewState().showProgress(false);
        lastDisposableQuery = null;
        end = true;
        Log.d("", throwable.toString());
        showMessageListEmpty();
    }

    private void showMessageListEmpty() {
        if (model.getImages().size() == 0) {
            getViewState().showNotFoundMessage();
        }
    }

    private void doOnSuccess(ImagesResponse imagesResponse) {
        getViewState().showProgress(false);
        getViewState().refreshImageList();
        lastDisposableQuery = null;
        showMessageListEmpty();
    }


    class RvPresenter implements IRvImageListPresenter {

        @Override
        public void bindView(@NonNull IImageListViewHolder viewHolder) {
            viewHolder.showProgress(true);
            viewHolder.setImage(model.getImages().get(viewHolder.getPos()).getPreviewURL());
        }

        @Override
        public int getItemCount() {
            return model.getImages().size();
        }

        @Override
        public void onImageLoaded(@NonNull final IImageListViewHolder viewHolder) {
            viewHolder.showProgress(false);
        }

        @Override
        public void onImageLoadFailed(@NonNull final IImageListViewHolder viewHolder) {
            viewHolder.showProgress(false);
        }
    }
}
