package com.mss.instamat.tests.mockito.di;

import android.support.annotation.NonNull;

import com.mss.instamat.tests.mockito.model.GithubApi;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;

@Module
public class TestModule {

    @NonNull
    @Provides
    public GithubApi provideGithubApi() {
        return Mockito.mock(GithubApi.class);
    }
}
