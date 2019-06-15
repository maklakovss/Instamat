package com.mss.instamat.repositories.files;

import com.mss.instamat.domain.models.ImageInfo;
import com.mss.instamat.domain.repositories.FilesRepository;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FilesRepositoryImplTest {

    private FilesRepository filesRepository;

    @Mock
    private FilesHelper filesHelper;

    @Before
    public void setUp() {
        filesRepository = spy(new FilesRepositoryImpl(filesHelper));
    }

    @Test
    public void saveBitmap() throws IOException {
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setId(1);
        when(filesHelper.getFolderPath()).thenReturn("path");
        when(filesHelper.getFilePath("path", imageInfo)).thenReturn("path\\1");

        filesRepository.saveBitmap(imageInfo, null);

        verify(filesHelper).getFolderPath();
        verify(filesHelper).createFolderIfNotExist("path");
        verify(filesHelper).getFilePath("path", imageInfo);
        verify(filesHelper).deleteFileIfExist("path\\1");
        verify(filesHelper).saveBitmapToStream(any(), any());
    }
}