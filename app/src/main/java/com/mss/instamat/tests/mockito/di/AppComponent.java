package com.mss.instamat.tests.mockito.di;

import android.support.annotation.NonNull;

import com.mss.instamat.tests.mockito.presenter.GithubFieldPresenter;
import com.mss.instamat.tests.mockito.view.GithubActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(@NonNull GithubActivity githubActivity);

    void inject(@NonNull GithubFieldPresenter githubFieldPresenter);
}
