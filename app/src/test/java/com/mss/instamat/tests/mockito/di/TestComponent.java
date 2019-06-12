package com.mss.instamat.tests.mockito.di;

import com.mss.instamat.tests.mockito.presenter.GithubFieldPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestModule.class})
public interface TestComponent {
    void inject(GithubFieldPresenter githubFieldPresenterTest);
}
