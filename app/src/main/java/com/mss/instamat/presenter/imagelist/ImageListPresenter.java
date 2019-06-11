package com.mss.instamat.presenter.imagelist;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mss.instamat.domain.ImageListModel;
import com.mss.instamat.view.imagelist.IImageListViewHolder;
import com.mss.instamat.view.imagelist.ImageListView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

@InjectViewState
public class ImageListPresenter extends MvpPresenter<ImageListView> {

    private final RvPresenter rvPresenter;
    private final ImageListModel model;

    private String lastQuery = "";
    private int nextPage = 1;
    private Disposable lastDisposableQuery = null;
    private boolean end = false;
    private Boolean inProgress = false;

    @Inject
    public ImageListPresenter(@NonNull final ImageListModel model) {
        this.model = model;
        rvPresenter = new RvPresenter();
    }

    public void onItemClick(int position) {
        Timber.d("onItemClick");
        getViewState().openDetailActivity(position);
    }

    @NonNull
    public RvPresenter getRvPresenter() {
        return rvPresenter;
    }

    public void onNeedNextPage(@NonNull final String searchText) {
        Timber.d("onNeedNextPage");
        synchronized (inProgress) {
            if (inProgress) {
                Timber.d("in progress, return");
                return;
            }
            inProgress = true;
        }
        if (!searchText.equals(lastQuery)) {
            stopNetworkQuery();
            initNewQuery(searchText);
        }
        if (lastDisposableQuery == null) {
            getViewState().showProgress(true);
            model.getImagesFromCacheDB(searchText, nextPage)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(imagesDB -> {
                        Timber.d("From cache database returns %d images on query '%s'", imagesDB.size(), searchText);
                        if (imagesDB.size() == 0) {
                            lastDisposableQuery = model.getImagesFromNetwork(searchText, nextPage)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(imagesNet -> {
                                                Timber.d("From network returns %d images on query '%s'", imagesNet.size(), searchText);
                                                model.saveToCacheDBAsync(searchText, nextPage, imagesNet)
                                                        .subscribe();
                                                doOnSuccess();
                                            },
                                            this::doOnError);
                        } else {
                            doOnSuccess();
                        }
                    });

        }
    }

    private void initNewQuery(@NonNull final String searchText) {
        Timber.d("initNewQuery");
        lastQuery = searchText;
        nextPage = 1;
        end = false;
        model.clearImages();
        getViewState().refreshImageList();
    }

    private void stopNetworkQuery() {
        if (lastDisposableQuery != null) {
            Timber.d("stopNetworkQuery");
            lastDisposableQuery.dispose();
            lastDisposableQuery = null;
        }
    }

    private void doOnError(@NonNull final Throwable throwable) {
        Timber.d(throwable);
        inProgress = false;
        getViewState().showProgress(false);
        lastDisposableQuery = null;
        end = true;
        showMessageListEmpty();
    }

    private void showMessageListEmpty() {
        if (model.getImages().size() == 0) {
            Timber.d("showMessageListEmpty");
            getViewState().showNotFoundMessage();
        }
    }

    private void doOnSuccess() {
        Timber.d("doOnSuccess");
        inProgress = false;
        nextPage += 1;
        getViewState().showProgress(false);
        getViewState().refreshImageList();
        lastDisposableQuery = null;
        showMessageListEmpty();
    }


    class RvPresenter implements IRvImageListPresenter {

        @Override
        public void bindView(@NonNull final IImageListViewHolder viewHolder) {
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
