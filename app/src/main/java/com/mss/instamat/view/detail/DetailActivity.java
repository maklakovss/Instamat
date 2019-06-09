package com.mss.instamat.view.detail;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jsibbold.zoomage.ZoomageView;
import com.mss.instamat.App;
import com.mss.instamat.R;
import com.mss.instamat.presenter.detail.DetailPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends MvpAppCompatActivity implements DetailView {

    public static final String PARAMETER_POSITION_TAG = "PARAMETER_POSITION_TAG";

    private int position = 0;

    @Inject
    @InjectPresenter
    DetailPresenter presenter;

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
        App.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt(PARAMETER_POSITION_TAG, 0);
        }
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
                if (imageView.getDrawable() != null) {
                    final BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                    presenter.onSaveClick(position, drawable.getBitmap());
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showImage(@NonNull final String imageURL) {
        Glide
                .with(this)
                .load(imageURL)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        presenter.onImageLoadFailed();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        presenter.onImageLoaded();
                        return false;
                    }
                })
                .into(imageView);
    }

    @Override
    public void showProgress(boolean visible) {
        if (visible) {
            pbDetail.setVisibility(View.VISIBLE);
        } else {
            pbDetail.setVisibility(View.GONE);
        }
    }

    @Override
    public void showSuccessSaveMessage() {
        Snackbar.make(imageView, R.string.image_saved_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showFailedSaveMessage() {
        Snackbar.make(imageView, R.string.image_failed_message, Snackbar.LENGTH_SHORT).show();
    }
}
