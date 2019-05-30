package com.mss.instamat.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mss.instamat.model.ImageListModel;
import com.mss.instamat.view.imagelist.IImageListViewHolder;
import com.mss.instamat.view.imagelist.ImageListView;

@InjectViewState
public class ImageListPresenter extends MvpPresenter<ImageListView> {

    private static String TAG = "ImageListPresenter";
    private final ImageListModel model = ImageListModel.getInstance();
    private final RvPresenter rvPresenter = new RvPresenter();


    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().initImageList(model.getImages());
    }

    public void onItemClick(int position) {
        model.setCountClick(model.getCountClick() + 1);
        getViewState().openDetailActivity(position);
        Log.d(TAG, String.valueOf(model.getCountClick()));
    }

    @NonNull
    public RvPresenter getRvPresenter() {
        return rvPresenter;
    }


    class RvPresenter implements IRvImageListPresenter {

        @Override
        public void bindView(@NonNull IImageListViewHolder viewHolder) {
            viewHolder.setImage(model.getImages().get(viewHolder.getPos()));
        }

        @Override
        public int getItemCount() {
            return model.getImages().size();
        }
    }
}
