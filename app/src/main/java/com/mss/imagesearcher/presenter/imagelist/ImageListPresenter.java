package com.mss.imagesearcher.presenter.imagelist;

import androidx.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mss.imagesearcher.domain.ImageListModel;
import com.mss.imagesearcher.view.imagelist.IImageListViewHolder;
import com.mss.imagesearcher.view.imagelist.ImageListView;

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

    public void onSearchClick(String searchText) {
        Timber.d("onSearchClick");
        if (searchText.equals(lastQuery)) {
            onRefresh(searchText);
        } else {
            startSearch(searchText);
        }
    }

    public void onNeedNextPage() {
        Timber.d("onNeedNextPage");
        if (end) {
            Timber.d("End results, return");
            return;
        }
        synchronized (this) {
            if (inProgress) {
                Timber.d("in progress, return");
                return;
            }
            inProgress = true;
        }
        getNextPage();
    }

    public void onRefresh(String searchText) {
        model.clearImages();
        startSearch(searchText);
//        model
//                .deleteImagesFromCache(searchText)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(() -> {
//                            getViewState().stopRefreshing();
//                            startSearch(searchText);
//                        },
//                        throwable -> {
//                            Timber.e(throwable);
//                            doOnError(throwable);
//                        });
    }


    private void startSearch(String searchText) {
        inProgress = true;
        stopNetworkQuery();
        initNewQuery(searchText);
        getNextPage();
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

    private void getNextPage() {
        getViewState().showProgress(true);
        loadImagesFromNetwork();
//        lastDisposableQuery = model.getImagesFromCacheDB(lastQuery, nextPage)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(imagesDB -> {
//                            Timber.d("From cache database returns %d images on query '%s'", imagesDB.size(), lastQuery);
//                            if (imagesDB.size() == 0) {
//                                loadImagesFromNetwork();
//                            } else {
//                                doOnSuccess();
//                            }
//                        },
//                        throwable -> {
//                            Timber.e(throwable);
//                            loadImagesFromNetwork();
//                        }
//                );
    }

    private void loadImagesFromNetwork() {
        lastDisposableQuery = model.getImagesFromNetwork(lastQuery, nextPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imagesNet -> {
                            Timber.d("From network returns %d images on query '%s'", imagesNet.size(), lastQuery);
//                            model.saveToCacheDBAsync(lastQuery, nextPage, imagesNet)
//                                    .subscribe(
//                                            list -> Timber.i("Saved to cache database %d images on query '%s' page %d",
//                                                    list.size(),
//                                                    lastQuery,
//                                                    nextPage),
//                                            Timber::e);
                            doOnSuccess();
                        },
                        this::doOnError);
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

    public void onCreate() {
        getViewState().initAdMob();
    }

    public void onPrivacyPolicyClick() {
        getViewState().showPrivacyPolicy();
    }


    public class RvPresenter implements IRvImageListPresenter {

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
