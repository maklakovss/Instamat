package com.mss.instamat.tests.mockito.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mss.instamat.tests.mockito.model.GithubApi;
import com.mss.instamat.tests.mockito.presenter.GithubConstructorPresenter;
import com.mss.instamat.tests.mockito.presenter.GithubFieldPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @NonNull
    @Provides
    @Singleton
    Context provideAppContext() {
        return app.getApplicationContext();
    }

    @NonNull
    @Provides
    @Singleton
    GithubApi provideGithubApi() {
        return new GithubApi();
    }

    @NonNull
    @Provides
    @Singleton
    GithubConstructorPresenter provideGithubConstructorPresenter(@NonNull GithubApi githubApi) {
        return new GithubConstructorPresenter(githubApi);
    }

    @NonNull
    @Provides
    @Singleton
    GithubFieldPresenter provideGithubFieldPresenter() {
        return new GithubFieldPresenter();
    }
}
