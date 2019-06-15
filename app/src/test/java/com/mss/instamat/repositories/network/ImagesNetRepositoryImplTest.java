package com.mss.instamat.repositories.network;

import com.mss.instamat.domain.repositories.ImagesNetRepository;
import com.mss.instamat.repositories.network.models.ImageInfoNet;
import com.mss.instamat.repositories.network.models.ImagesResponse;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ImagesNetRepositoryImplTest {

    private ImagesNetRepository repository;

    @Mock
    private PixabayAPI pixabayAPI;

    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @Before
    public void setUp() {
        repository = new ImagesNetRepositoryImpl(pixabayAPI);
    }

    @Test
    public void findImages_callAPIAndReturnsMappedImages() {
        final ImagesResponse imagesResponse = getImagesResponse();
        when(pixabayAPI.findImages(anyString(), anyString(), anyString(), anyString(), anyInt(), anyInt())).thenReturn(Maybe.just(imagesResponse));

        assertEquals(imagesResponse.getHits().size(), repository.findImages("one", 1).blockingGet().size());

        verify(pixabayAPI).findImages(anyString(), anyString(), anyString(), anyString(), anyInt(), anyInt());
    }

    @NotNull
    private ImagesResponse getImagesResponse() {
        ImagesResponse imagesResponse = new ImagesResponse();
        ImageInfoNet imageInfoNet = new ImageInfoNet();
        List<ImageInfoNet> imagesNet = new ArrayList<>();
        imagesNet.add(imageInfoNet);
        imagesResponse.setHits(imagesNet);
        return imagesResponse;
    }
}