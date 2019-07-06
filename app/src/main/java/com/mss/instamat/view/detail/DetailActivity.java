package com.mss.instamat.view.detail;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.jsibbold.zoomage.ZoomageView;
import com.mss.instamat.App;
import com.mss.instamat.R;
import com.mss.instamat.presenter.detail.DetailPresenter;
import com.mss.instamat.view.helpers.ImageLoader;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.Timber;

public class DetailActivity extends MvpAppCompatActivity implements DetailView {

    public static final String PARAMETER_POSITION_TAG = "PARAMETER_POSITION_TAG";

    private static final int PERMISSION_REQUEST_SAVE = 555;
    private static final int PERMISSION_REQUEST_SHARE = 888;
    private static final String[] STORAGE_PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private int position = 0;

    @Inject
    @InjectPresenter
    DetailPresenter presenter;

    @Inject
    ImageLoader imageLoader;

    @BindView(R.id.pbDetail)
    ProgressBar pbDetail;

    @BindView(R.id.imageView)
    ZoomageView imageView;

    @NonNull
    @ProvidePresenter
    DetailPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.d("onCreate");
        App.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        getParameters();
        presenter.onCreate(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miSave:
                saveImageWithCheckPermission();
                return true;
            case R.id.miShare:
                shareImageWithCheckPermission();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Timber.d("onRequestPermissionsResult requestCode = %d, grantResultsSize = %s", requestCode, grantResults.length);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void showImage(@NonNull final String imageURL) {
        Timber.d("showImage");
        imageLoader.load(this,
                imageURL,
                imageView,
                () -> presenter.onImageLoaded(),
                () -> presenter.onImageLoadFailed());
    }

    @Override
    public void showProgress(boolean visible) {
        Timber.d("showProgress %b", visible);
        if (visible) {
            pbDetail.setVisibility(View.VISIBLE);
        } else {
            pbDetail.setVisibility(View.GONE);
        }
    }

    @Override
    public void showSuccessSaveMessage() {
        Timber.d("showSuccessSaveMessage");
        Snackbar.make(imageView, R.string.image_saved_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showFailedSaveMessage() {
        Timber.d("showFailedSaveMessage");
        Snackbar.make(imageView, R.string.image_failed_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void shareImage(@NonNull final String path) {
        Timber.d("shareImage");
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
        startActivity(Intent.createChooser(intent, "Share Image"));
    }

    @AfterPermissionGranted(PERMISSION_REQUEST_SAVE)
    private void saveImageWithCheckPermission() {
        if (hasStoragePermission()) {
            saveImage();
        } else {
            requestPermission(PERMISSION_REQUEST_SAVE);
        }
    }

    private void requestPermission(int permissionRequestCode) {
        Timber.d("requestPermission");
        EasyPermissions.requestPermissions(this, getString(R.string.storage_rationale), permissionRequestCode, STORAGE_PERMISSIONS);
    }

    private void saveImage() {
        Timber.d("saveImage");
        final BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        if (drawable != null) {
            presenter.onSaveClick(position, drawable.getBitmap());
        }
    }

    @AfterPermissionGranted(PERMISSION_REQUEST_SHARE)
    private void shareImageWithCheckPermission() {
        if (hasStoragePermission()) {
            shareImage();
        } else {
            requestPermission(PERMISSION_REQUEST_SHARE);
        }
    }

    private void shareImage() {
        Timber.d("shareImage");
        final BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        if (drawable != null) {
            presenter.onShareClick(position, drawable.getBitmap());
        }
    }

    private boolean hasStoragePermission() {
        return EasyPermissions.hasPermissions(this, STORAGE_PERMISSIONS);
    }

    private void getParameters() {
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt(PARAMETER_POSITION_TAG, 0);
        }
    }
}
