package com.mss.instamat.view.helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import timber.log.Timber;

public class ImageLoaderImpl implements ImageLoader {

    @Override
    public void load(@NonNull Context context,
                     @NonNull String path,
                     @NonNull ImageView imageView,
                     @Nullable Runnable onSuccess,
                     @Nullable Runnable onFailure) {
        Glide
                .with(context)
                .load(path)
                .addListener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Timber.e(e);
                        if (onFailure != null) {
                            onFailure.run();
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Timber.d("Image loaded");
                        if (onSuccess != null) {
                            onSuccess.run();
                        }
                        return false;
                    }
                })
                .into(imageView);
    }
}
