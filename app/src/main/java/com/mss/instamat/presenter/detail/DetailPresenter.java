package com.mss.instamat.presenter.detail;

import android.graphics.Bitmap;
import android.os.Environment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mss.instamat.model.ImageListModel;
import com.mss.instamat.view.detail.DetailView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailView> {

    private static final String SAVE_FOLDER = "instamat";
    private final ImageListModel model;

    @Inject
    public DetailPresenter(ImageListModel model) {
        this.model = model;
    }

    public void onCreate(int position) {
        getViewState().showProgress(true);
        getViewState().showImage(model.getImages().get(position).getLargeImageURL());
    }

    public void onImageLoaded() {
        getViewState().showProgress(false);
    }

    public void onImageLoadFailed() {
        getViewState().showProgress(false);
    }

    public void onSaveClick(int position, Bitmap bitmap) {
        try {
            File storageLoc = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File myDir = new File(storageLoc.getAbsoluteFile() + File.separator + SAVE_FOLDER);
            myDir.mkdirs();
            File file = new File(myDir, model.getImages().get(position).getId() + ".jpg");
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
            getViewState().showSuccessSaveMessage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            getViewState().showFailedSaveMessage();
        } catch (IOException e) {
            e.printStackTrace();
            getViewState().showFailedSaveMessage();
        }
    }
}
