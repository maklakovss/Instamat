package com.mss.imagesearcher.view.helpers;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ImageLoader {

    void load(@NonNull Context context,
              @NonNull String path,
              @NonNull ImageView imageView,
              @Nullable Runnable onSuccess,
              @Nullable Runnable onFailure);
}
