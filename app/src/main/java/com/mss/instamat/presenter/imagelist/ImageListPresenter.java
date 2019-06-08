package com.mss.instamat.presenter.imagelist;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mss.instamat.model.ImageListModel;
import com.mss.instamat.view.imagelist.IImageListViewHolder;
import com.mss.instamat.view.imagelist.ImageListView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ImageListPresenter extends MvpPresenter<ImageListView> {

    private final RvPresenter rvPresenter;
    private final ImageListModel model;

    private String lastQuery = "";
    private int nextPage = 1;
    private Disposable lastDisposableQuery = null;
    private boolean end = false;

    @Inject
    public ImageListPresenter(ImageListModel model) {
        this.model = model;
        rvPresenter = new RvPresenter();
    }

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
            nextPage = 1;
            end = false;
            model.clearImages();
            getViewState().refreshImageList();
        }
        if (lastDisposableQuery == null) {
            getViewState().showProgress(true);

            model.getImagesFromCacheDB(searchText, nextPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(images -> {
                        if (images.size() == 0) {
                            lastDisposableQuery = model.getImagesFromNetwork(searchText, nextPage)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(imagesResponse -> {
                                                model.saveToCacheDBAsync(searchText, nextPage, imagesResponse.getHits());
                                                doOnSuccess();
                                            },
                                            this::doOnError);
                        } else {
                            doOnSuccess();
                        }
                    });

        }
    }

    private void doOnError(Throwable throwable) {
        getViewState().showProgress(false);
        lastDisposableQuery = null;
        end = true;
        Log.e("", throwable.toString());
        showMessageListEmpty();
    }

    private void showMessageListEmpty() {
        if (model.getImages().size() == 0) {
            getViewState().showNotFoundMessage();
        }
    }

    private void doOnSuccess() {
        nextPage += 1;
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
