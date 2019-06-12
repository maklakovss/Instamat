package com.mss.instamat.tests.mockito.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mss.instamat.tests.mockito.model.GithubApi;
import com.mss.instamat.tests.mockito.view.GithubView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

@InjectViewState
public class GithubConstructorPresenter extends MvpPresenter<GithubView> {

    private final GithubApi githubApi;

    @Inject
    public GithubConstructorPresenter(@NonNull GithubApi githubApi) {
        this.githubApi = githubApi;
    }

    public void onClick() {
        Timber.d("onClick");
        githubApi.getUser("JakeWharton")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                            Timber.d("onSuccess %s", user.login);
                            getViewState().showName(user.login);
                        },
                        throwable -> {
                            getViewState().showName("Error");
                            Timber.e(throwable);
                        });
    }
}
