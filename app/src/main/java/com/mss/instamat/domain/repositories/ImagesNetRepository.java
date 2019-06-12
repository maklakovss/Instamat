package com.mss.instamat.domain.repositories;

import android.support.annotation.NonNull;

import com.mss.instamat.domain.models.ImageInfo;

import java.util.List;

import io.reactivex.Maybe;

public interface ImagesNetRepository {

    @NonNull
    Maybe<List<ImageInfo>> findImages(@NonNull final String query, int pageNumber);
}
