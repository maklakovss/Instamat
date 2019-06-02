package com.mss.instamat.view.detail;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mss.instamat.R;
import com.mss.instamat.presenter.DetailPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends MvpAppCompatActivity implements DetailView {

    public static String POSITION_TAG = "POSITION_TAG";

    @BindView(R.id.pbDetail)
    ProgressBar pbDetail;

    @InjectPresenter
    DetailPresenter presenter;

    @BindView(R.id.imageView)
    ImageView imageView;
    private int position = 0;

    @ProvidePresenter
    DetailPresenter providePresenter() {
        return new DetailPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt(POSITION_TAG, 0);
        }
        presenter.onCreate(position);
    }

    @Override
    public void showImage(String imageURL) {
        Glide
                .with(this)
                .load(imageURL)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
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
}
