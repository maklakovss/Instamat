package com.mss.imagesearcher.domain.repositories;

import androidx.annotation.NonNull;

import com.mss.imagesearcher.domain.models.ImageInfo;

import java.util.List;

import io.reactivex.Maybe;

public interface ImagesNetRepository {

    @NonNull
    Maybe<List<ImageInfo>> findImages(@NonNull final String query, int pageNumber);
}
