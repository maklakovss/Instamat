package com.mss.instamat.view.helpers;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

public interface ImageLoader {

    void load(@NonNull Context context,
              @NonNull String path,
              @NonNull ImageView imageView,
              @Nullable Runnable onSuccess,
              @Nullable Runnable onFailure);
}
